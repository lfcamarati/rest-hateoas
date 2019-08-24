package io.github.lfcamarati.resthateoas.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.lfcamarati.resthateoas.core.jsonplaceholder.Post;
import io.github.lfcamarati.resthateoas.core.jsonplaceholder.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

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
        // Arrange
        final Post post = new Post();
        final User user = new User();

        // Act
        Map<String, Object> actual = fixture.create(post);

        // Assert
        System.out.println(gson.toJson(actual));
    }
}