package com.patients.patients.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
    private @Id
    @GeneratedValue Long id;
    private String content;
    private String photo;


    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
    private User owner;


    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "POST_TAG_MAPPING", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Post))
            return false;
        Post post = (Post) o;
        return Objects.equals(this.id, post.id)
                && Objects.equals(this.photo, post.photo) && Objects.equals(this.content, post.content) && Objects.equals(this.owner, post.owner);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.id,  this.content, this.owner);
    }
    @Override
    public String toString() {
        return "Post{" + "id=" + this.id + '\'' + "content=" + this.content + '\''+  '}';
    }
}
