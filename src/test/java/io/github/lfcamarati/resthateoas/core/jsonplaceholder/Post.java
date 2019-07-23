package io.github.lfcamarati.resthateoas.core.jsonplaceholder;

import io.github.lfcamarati.resthateoas.annotations.Self;
import io.github.lfcamarati.resthateoas.core.Resource;

public class Post implements Resource {

    @Self("https://jsonplaceholder.typicode.com/posts/{id}")
    private Long id;
}
