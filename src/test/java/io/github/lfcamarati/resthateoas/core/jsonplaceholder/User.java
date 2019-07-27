package io.github.lfcamarati.resthateoas.core.jsonplaceholder;

import io.github.lfcamarati.resthateoas.annotations.Self;

public class User {

    @Self("https://jsonplaceholder.typicode.com/users/{id}")
    private Long id = 1L;

    private String name = "Leanne Graham";
    private String username = "Bret";
    private String email = "Sincere@april.biz";

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
