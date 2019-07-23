package io.github.lfcamarati.resthateoas.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.lfcamarati.resthateoas.core.jsonplaceholder.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceBuilderTest {

    private ResourceBuilder fixture;
    private Gson gson;

    @BeforeEach
    void setUp() {
        fixture = new ResourceBuilder();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Test
    void first() {
        final Post post = new Post();
        post.setId(10L);

        ResourceBase actual = fixture.create(post);
        System.out.println(gson.toJson(actual));
    }
}