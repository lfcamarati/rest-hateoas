package io.github.lfcamarati.resthateoas.core;

public class LinkImpl {
    static final String LINKS = "_links";

    private String key;
    private String href;

    private LinkImpl() {}

    public LinkImpl(String href) {
        this.href = href;
    }

    public LinkImpl(String key, String href) {
        this.key = key;
        this.href = href;
    }

    public String getKey() {
        return key;
    }

    public String getHref() {
        return href;
    }
}
