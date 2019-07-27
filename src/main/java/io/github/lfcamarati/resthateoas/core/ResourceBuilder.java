package io.github.lfcamarati.resthateoas.core;

import io.github.lfcamarati.resthateoas.annotations.Embedded;
import io.github.lfcamarati.resthateoas.annotations.Link;
import io.github.lfcamarati.resthateoas.reflect.ReflectionUtils;

import java.util.List;

public class ResourceBuilder {

    private Object resource;
    private Class<? extends Object> klass;

    private List<Link> links;
    private List<Embedded> embeddeds;

    public ResourceBuilder() {

    }

    public ResourceBase create(Object resource) {
        if(resource == null) {
            throw new IllegalArgumentException("Resource is empty");
        }

        ResourceBase result = new ResourceBase();
        ReflectionUtils reflection = new ReflectionUtils(resource);
        reflection.appy(result);

        return result;
    }

}
