package com.patients.patients.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    private @Id
    @GeneratedValue Long id;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String name;
    private String role;

    @OneToMany(mappedBy = "owner",orphanRemoval = true,cascade = CascadeType.ALL )
    private List<Post> posts;

    public User essence() {
        return new User(this.id, this.email, this.password,this.name,this.role,this.posts);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id)
                && Objects.equals(this.email, user.email) && Objects.equals(this.password, user.password)&& Objects.equals(this.name, user.name)&& Objects.equals(this.role, user.role);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.id,  this.email, this.password, this.name,this.role);
    }
    @Override
    public String toString() {
        return "User{" + "id=" + this.id + '\'' + ", email='" + this.email + '\'' +", password='" + this.password + + '\'' +", name='" + this.name + + '\'' +", role='" + this.role + + '\'' +  '}';
    }
}
