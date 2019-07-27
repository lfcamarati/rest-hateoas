package io.github.lfcamarati.resthateoas.reflect;

import io.github.lfcamarati.resthateoas.annotations.Embedded;
import io.github.lfcamarati.resthateoas.annotations.Link;
import io.github.lfcamarati.resthateoas.annotations.Self;
import io.github.lfcamarati.resthateoas.core.ResourceBase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class ReflectionUtils {

    private Object resource;
    private Class<? extends Object> klass;

    private List<Link> links = new ArrayList<>();
    private Self self = null;
    Map<String, Object> embeddeds = new HashMap<>();

    public ReflectionUtils(Object resource) {
        this.resource = resource;
        this.klass = resource.getClass();

        processSelf();
        processLinks();
        processEmbeddeds();
    }

    private void processSelf() {
        List<Field> fields = getFields(Self.class);

        if(fields.isEmpty()) {
            return;
        }

        if(fields.size() > 1) {
            throw new IllegalStateException("Only 1 self is allowed");
        }

        this.self = fields.get(0).getAnnotation(Self.class);
    }

    private void processLinks() {
        Link[] links = klass.getAnnotationsByType(Link.class);

        if(links == null || links.length == 0) {
            return;
        }

        this.links = of(links).collect(toList());
    }
    
    private void processEmbeddeds() {
        try {
            List<Field> fields = getFields(Embedded.class);

            embeddeds = new HashMap<>();

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(resource);

                if (value != null) {
                    embeddeds.put(field.getName(), value);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<Field> getFields(Class<? extends Annotation> annotationClass) {
        return of(klass.getDeclaredFields()).filter(f -> f.isAnnotationPresent(annotationClass))
                .collect(toList());
    }

    public void appy(ResourceBase resourceBase) {
        resourceBase.apply(self, links, embeddeds);
    }
}
