package com.patients.patients.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Tag {
    private @Id
    @GeneratedValue Long id;

    @Column(unique=true)
    private String title;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "POST_TAG_MAPPING", joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))

    private Set<Post> taggedPosts;
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        Tag tag = (Tag) o;
        return Objects.equals(this.id, tag.id)
                && Objects.equals(this.title, tag.title) ;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.id,  this.title);
    }
    @Override
    public String toString() {
        return "Tag{" + "id=" + this.id + '\'' + "title=" + this.title + '\''+  '}';
    }
}
