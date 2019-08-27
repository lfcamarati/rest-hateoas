package io.github.lfcamarati.resthateoas.core;

public class SelfImpl extends LinkImpl {
    public static final String SELF = "self";

    private String href;

    public SelfImpl(String href) {
        super(href);
    }
}
