package io.github.lfcamarati.resthateoas.core;

class Link {
    static final String SELF = "self";

    private String href;

    public Link(String href) {
        this.href = href;
    }
}
