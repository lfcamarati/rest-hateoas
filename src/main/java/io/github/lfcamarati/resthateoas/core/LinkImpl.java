package io.github.lfcamarati.resthateoas.core;

class LinkImpl {
    static final String LINKS = "_links";
    static final String SELF = "self";

    private String href;

    private LinkImpl() {}

    public LinkImpl(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }
}
