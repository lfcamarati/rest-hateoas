package io.github.lfcamarati.resthateoas.core;

import io.github.lfcamarati.resthateoas.annotations.Embedded;
import io.github.lfcamarati.resthateoas.annotations.Link;
import io.github.lfcamarati.resthateoas.annotations.Self;
import io.github.lfcamarati.resthateoas.reflect.ReflectionUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceBuilder {

    private Object resource;
    private Class<? extends Object> klass;

    private List<Link> links;
    private List<Embedded> embeddeds;

    public ResourceBuilder() {

    }

    public Map<String, Object> create(Object resource) {
        if(resource == null) {
            throw new IllegalArgumentException("Resource is empty");
        }

        ReflectionUtils reflection = new ReflectionUtils(resource);
        return apply(reflection.self(), reflection.links(), reflection.embeddeds(), reflection.getSimpleFields());
    }

    private Map<String, Object> apply(SelfImpl self, List<LinkImpl> links, Map<String, Object> embeddeds,
            Map<String, Object> simpleFields) {

        Map<String, Object> attrs = new HashMap<>();
        Map<String, LinkImpl> linksValue = new HashMap<>();
        Map<String, Object> embeddedsValue = new HashMap<>();

        if(self != null) {
            linksValue.put(SelfImpl.SELF, new LinkImpl(SelfImpl.SELF, self.getHref()));
        }

        for(LinkImpl link : links) {
            linksValue.put(link.getKey(), link);
        }

        for(Map.Entry<String, Object> embedded : embeddeds.entrySet()) {
            if(embedded.getValue() != null && embedded.getValue().getClass().isArray()) {
                embeddedsValue.put(embedded.getKey(), embedded.getValue());
            } else {
                embeddedsValue.put(embedded.getKey(), create(embedded.getValue()));
            }
        }

        for(Map.Entry<String, Object> value : simpleFields.entrySet()) {
            attrs.put(value.getKey(), value.getValue());
        }

        if(!linksValue.isEmpty()) {
            attrs.put(LinkImpl.LINKS, linksValue);
        }

        if(!embeddedsValue.isEmpty()) {
            attrs.put(EmbeddedImpl.EMBEDDEDS, embeddedsValue);
        }

        if(!embeddedsValue.isEmpty()) {
            attrs.put(EmbeddedImpl.EMBEDDEDS, embeddedsValue);
        }

        return attrs;
    }
}
