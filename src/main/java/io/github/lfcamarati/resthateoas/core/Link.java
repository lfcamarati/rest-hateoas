package io.github.lfcamarati.resthateoas.core;

public class Link {
    static final String SELF = "self";

    private String href;

    public Link(String href) {
        this.href = href;
    }
}
