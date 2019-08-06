package io.github.lfcamarati.resthateoas.core;

import io.github.lfcamarati.resthateoas.annotations.Self;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResourceBase extends Resource {

    private Map<String, Link> _links = new HashMap<>();
    private Map<String, ResourceBase> _embedded = new HashMap<>();

    private void self(String href) {
        _links.put(Link.SELF, new Link(href));
    }

    private void link(String key, String href) {
        _links.put(key, new Link(href));
    }

    private void embedded(String key, ResourceBase resourceBase) {
        _embedded.put(key, resourceBase);
    }

    public void apply(Self self, List<io.github.lfcamarati.resthateoas.annotations.Link> links, Map<String, Object> embeddeds) {
        if(self != null) {
            this.self(self.value());
        }

        for(io.github.lfcamarati.resthateoas.annotations.Link link : links) {
            this.link(link.key(), link.href());
        }

        Set<Map.Entry<String, Object>> embeddedEntries = embeddeds.entrySet();

        for(Map.Entry<String, Object> embedded : embeddedEntries) {
            this.embedded(embedded.getKey(), new ResourceBuilder().create(embedded.getValue()));
        }
    }

    public Map<String, Link> get_links() {
        return _links;
    }

    public Map<String, ResourceBase> get_embedded() {
        return _embedded;
    }
}
