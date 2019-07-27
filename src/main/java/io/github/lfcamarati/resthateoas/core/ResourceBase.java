package io.github.lfcamarati.resthateoas.core;

import java.util.HashMap;
import java.util.Map;

class ResourceBase implements Resource {

    private Map<String, Link> _links = new HashMap<>();
    private Map<String, ResourceBase> _embedded = new HashMap<>();

    void self(String href) {
        _links.put(Link.SELF, new Link(href));
    }

    void link(String key, String href) {
        _links.put(key, new Link(href));
    }

    void embedded(String key, ResourceBase resourceBase) {
        _embedded.put(key, resourceBase);
    }

    public Map<String, Link> get_links() {
        return _links;
    }

    public Map<String, ResourceBase> get_embedded() {
        return _embedded;
    }
}
