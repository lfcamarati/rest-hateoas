package io.github.lfcamarati.resthateoas.reflect;

import io.github.lfcamarati.resthateoas.annotations.Embedded;
import io.github.lfcamarati.resthateoas.annotations.Link;
import io.github.lfcamarati.resthateoas.annotations.Self;
import io.github.lfcamarati.resthateoas.core.LinkImpl;
import io.github.lfcamarati.resthateoas.core.SelfImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class ReflectionUtils {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");;

    private Object resource;
    private Class<? extends Object> klass;
    private Map<String, Object> simpleFields;

    public ReflectionUtils(Object resource) {
        this.resource = resource;
        this.klass = resource.getClass();
        processSimpleFields();
    }

    public SelfImpl self() {
        List<Field> fields = getFields(Self.class);

        if(fields.isEmpty()) {
            return null;
        }

        if(fields.size() > 1) {
            throw new IllegalStateException("Only 1 self is allowed");
        }

        Self self = fields.get(0).getAnnotation(Self.class);

        return new SelfImpl(replace(self.value()));
    }

    public List<LinkImpl> links() {
        Link[] linksAnnotations = klass.getAnnotationsByType(Link.class);

        if(linksAnnotations == null || linksAnnotations.length == 0) {
            return Collections.emptyList();
        }

        List<LinkImpl> links = new ArrayList<>();

        for(Link link : linksAnnotations) {
            links.add(new LinkImpl(link.key(), replace(link.href())));
        }

        return links;
    }
    
    public Map<String, Object> embeddeds() {
        List<Field> fields = getFields(Embedded.class);
        Map<String, Object> embeddeds = new HashMap<>();

        for (Field field : fields) {
            try {
                field.setAccessible(true);

                if (Collection.class.isAssignableFrom(field.getType())) {
                    Collection collection = (Collection) field.get(resource);
                    Object[] listValue = new Object[collection.size()];
                    int count = 0;

                    for(Object obj : collection) {
                        listValue[count] = obj;
                        count++;
                    }

                    embeddeds.put(field.getName(), listValue);
                } else {
                    Object value = field.get(resource);

                    if (value != null) {
                        embeddeds.put(field.getName(), value);
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return embeddeds;
    }

    public Map<String, Object> getSimpleFields() {
        return simpleFields;
    }

    private void processSimpleFields() {
        List<Field> fields = getSimpleFields(Self.class, Link.class, Embedded.class);
        Map<String, Object> values = new HashMap<>();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(resource);

                if (value != null) {
                    values.put(field.getName(), value);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        simpleFields = values;
    }

    private List<Field> getFields(Class<? extends Annotation> annotationClass) {
        return of(klass.getDeclaredFields()).filter(f -> f.isAnnotationPresent(annotationClass))
                .collect(toList());
    }

    private Field getField(String fieldName) {
        try {
            return klass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private String replace(String value) {
        Matcher m = PATTERN.matcher(value);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            String key = m.group(1);
            Field field = getField(key);
            String keyValueString;

            try {
                field.setAccessible(true);
                Object fieldValue = field.get(resource);

                if(fieldValue != null) {
                    keyValueString = fieldValue.toString();
                } else {
                    keyValueString = "??" + key + "??";
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                keyValueString = "!!" + key + "!!";
            }

            m.appendReplacement(sb, Matcher.quoteReplacement(keyValueString));
        }

        m.appendTail(sb);
        return sb.toString();
    }

    @SafeVarargs
    private final List<Field> getSimpleFields(Class<? extends Annotation>... annotationClass) {
        return of(klass.getDeclaredFields())
                       .filter(f -> Stream.of(annotationClass).noneMatch(f::isAnnotationPresent))
                       .collect(toList());
    }
}
