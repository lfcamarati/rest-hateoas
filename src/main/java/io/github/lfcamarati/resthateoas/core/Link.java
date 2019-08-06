package io.github.lfcamarati.resthateoas.core;

class Link {
    static final String SELF = "self";

    private String href;

    private Link() {}

    public Link(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }
}
