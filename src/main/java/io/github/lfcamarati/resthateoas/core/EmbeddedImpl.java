package io.github.lfcamarati.resthateoas.core;

class EmbeddedImpl {
    static final String EMBEDDEDS = "_embedded";

    private String href;

    public EmbeddedImpl(String href) {
        this.href = href;
    }
}
