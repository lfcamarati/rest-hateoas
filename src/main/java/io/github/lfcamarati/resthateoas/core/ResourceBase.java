package io.github.lfcamarati.resthateoas.core;

import java.util.HashMap;
import java.util.Map;

class ResourceBase implements Resource {

    private Map<String, Link> _links = new HashMap<>();

    void self(String href) {
        _links.put(Link.SELF, new Link(href));
    }

    public Map<String, Link> get_links() {
        return _links;
    }
}
