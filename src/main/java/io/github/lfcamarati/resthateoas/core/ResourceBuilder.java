package io.github.lfcamarati.resthateoas.core;

import io.github.lfcamarati.resthateoas.annotations.Self;

import java.lang.reflect.Field;

public class ResourceBuilder {

    public ResourceBase create(Resource resource) {
        ResourceBase result = new ResourceBase();

        Class<? extends Resource> klass = resource.getClass();
        Field[] fields = klass.getDeclaredFields();

        for(Field field : fields) {
            if(field.isAnnotationPresent(Self.class)) {
                Self self = field.getAnnotation(Self.class);
                result.self(self.value());
            }
        }

        return result;
    }

}
