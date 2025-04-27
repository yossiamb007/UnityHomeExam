package com.unity.api.model;

// PostRequest.java
public class Post {
    private String title;
    private String content;
    private String status;
    private boolean published;
    private String publisher;

    public Post(String title, String content, String status, boolean published, String publisher) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.published = published;
        this.publisher = publisher;
    }

    // Getters and Setters
}
