package io.github.lfcamarati.resthateoas.core.jsonplaceholder;

import io.github.lfcamarati.resthateoas.annotations.Embedded;
import io.github.lfcamarati.resthateoas.annotations.Link;
import io.github.lfcamarati.resthateoas.annotations.Self;
import io.github.lfcamarati.resthateoas.core.Resource;

import java.util.ArrayList;
import java.util.List;

@Link(key = "comments", href = "https://jsonplaceholder.typicode.com/posts/{id}/comments")
public class Post implements Resource {

    @Self("https://jsonplaceholder.typicode.com/posts/{id}")
    private Long id = 1L;

    private String title = "Some Title";
    private String body = "Body";

    @Embedded
    private List<Photo> photos = new ArrayList<>();

    @Embedded
    private User user = new User();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void add(Photo photo) {
        if(photos == null) {
            photos = new ArrayList<Photo>();
        }

        photos.add(photo);
    }
}
