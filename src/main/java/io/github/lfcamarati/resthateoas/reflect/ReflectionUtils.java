package io.github.lfcamarati.resthateoas.reflect;

import io.github.lfcamarati.resthateoas.annotations.Embedded;
import io.github.lfcamarati.resthateoas.annotations.Link;
import io.github.lfcamarati.resthateoas.annotations.Self;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class ReflectionUtils {

    private Object resource;
    private Class<? extends Object> klass;

    public ReflectionUtils(Object resource) {
        this.resource = resource;
        this.klass = resource.getClass();
    }

    public Self self() {
        List<Field> fields = getFields(Self.class);

        if(fields.isEmpty()) {
            return null;
        }

        if(fields.size() > 1) {
            throw new IllegalStateException("Only 1 self is allowed");
        }

        return fields.get(0).getAnnotation(Self.class);
    }

    public List<Link> links() {
        Link[] links = klass.getAnnotationsByType(Link.class);

        if(links == null || links.length == 0) {
            return Collections.emptyList();
        }

        return of(links).collect(toList());
    }
    
    public Map<String, Object> embeddeds() {
        List<Field> fields = getFields(Embedded.class);
        Map<String, Object> embeddeds = new HashMap<>();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(resource);

                if (value != null) {
                    embeddeds.put(field.getName(), value);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return embeddeds;
    }
    
    public Map<String, Object> simpleFields() {
        List<Field> simpleFields = getSimpleFields(Self.class, Link.class, Embedded.class);
        Map<String, Object> values = new HashMap<>();

        for (Field field : simpleFields) {
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

        return values;
    }

    private List<Field> getFields(Class<? extends Annotation> annotationClass) {
        return of(klass.getDeclaredFields()).filter(f -> f.isAnnotationPresent(annotationClass))
                .collect(toList());
    }

    @SafeVarargs
    private final List<Field> getSimpleFields(Class<? extends Annotation>... annotationClass) {
        return of(klass.getDeclaredFields())
                       .filter(f -> Stream.of(annotationClass).noneMatch(f::isAnnotationPresent))
                       .collect(toList());
    }
}
