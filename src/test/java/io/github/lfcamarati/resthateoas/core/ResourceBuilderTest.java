package io.github.lfcamarati.resthateoas.core;

import io.github.lfcamarati.resthateoas.core.jsonplaceholder.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceBuilderTest {

    private ResourceBuilder fixture;

    @BeforeEach
    void setUp() {
        fixture = new ResourceBuilder();
    }

    @Test
    void first() {
        final Post post = new Post();

        ResourceBase actual = fixture.create(post);

        assertEquals(1, actual.get_links().size());
    }
}