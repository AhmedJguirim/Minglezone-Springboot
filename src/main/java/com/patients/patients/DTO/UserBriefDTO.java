package com.patients.patients.DTO;

public class UserBriefDTO {
    private long id;
    private String name;
    private String email;

    public UserBriefDTO(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserBriefDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
