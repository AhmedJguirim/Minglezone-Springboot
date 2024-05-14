package com.patients.patients.DTO;

import com.patients.patients.entities.Tag;

import java.util.Set;

public class PostDTO {
    private Long id;
    private String content;
    private String photo;
    private Long ownerId;
    private Set<Tag> tags;

    public PostDTO() {
    }

    public PostDTO(Long id, String content, String photo, Long ownerId, Set<Tag> tags) {
        this.id = id;
        this.content = content;
        this.photo = photo;
        this.ownerId = ownerId;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
