package io.github.lfcamarati.resthateoas.core;

import java.util.Collections;
import java.util.Map;

public abstract class Resource {

    public Map<String, Link> get_links() {
        return Collections.emptyMap();
    }

    public Map<String, ResourceBase> get_embedded() {
        return Collections.emptyMap();
    }
}
