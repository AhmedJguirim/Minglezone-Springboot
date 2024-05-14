package com.patients.patients.repositories;
import com.patients.patients.entities.Post;
import com.patients.patients.entities.Tag;
import com.patients.patients.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
//    List<Tag> findByTitle(String name);
    Optional<Tag> findByTitle(String title);

}