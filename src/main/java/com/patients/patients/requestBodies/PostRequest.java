package com.patients.patients.requestBodies;

public class PostRequest {
    private String content;
    private String photo;
    private long ownerId;

    public String getContent() {
        return content;
    }

    public PostRequest(String content, String photo, long ownerId) {
        this.content = content;
        this.photo = photo;
        this.ownerId = ownerId;
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

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
