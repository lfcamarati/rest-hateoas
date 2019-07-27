package io.github.lfcamarati.resthateoas.core;

import io.github.lfcamarati.resthateoas.annotations.Link;
import io.github.lfcamarati.resthateoas.annotations.Self;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ResourceBuilder {

    public ResourceBase test(Resource resource) {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(ResourceBase.class);

        MethodHandler handler = (self, thisMethod, proceed, args) -> {
            System.out.println("Handling " + thisMethod + " via the method handler");
            return null;
        };

        try {
            return (ResourceBase) factory.create(new Class<?>[0], new Object[0], handler);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResourceBase create(Resource resource) {
        ResourceBase result = new ResourceBase();

        Class<? extends Resource> klass = resource.getClass();
        Field[] fields = klass.getDeclaredFields();

        for(Field field : fields) {
            if(field.isAnnotationPresent(Self.class)) {
                Self self = field.getAnnotation(Self.class);
                String linkRegexp = "\\{"+ field.getName() +"\\}";

                try {
                    field.setAccessible(true);
                    result.self(self.value().replaceAll(linkRegexp, field.get(resource).toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }

        Link[] classLinks = klass.getDeclaredAnnotationsByType(Link.class);

        Stream.of(classLinks).forEach(l -> {
            Object id = null;
            try {
                Field id1 = klass.getDeclaredField("id");
                id1.setAccessible(true);
                id = id1.get(resource);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            String linkRegexp = "\\{id\\}";
            result.link(l.key(), l.href().replaceAll(linkRegexp, id.toString()));
        });

        return result;
    }

}
