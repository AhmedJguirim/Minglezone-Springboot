package com.patients.patients.repositories;
import com.patients.patients.entities.Post;
import com.patients.patients.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByOwner(User owner);
    @Query(value = "SELECT * FROM Post ORDER BY id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Post> findPaginatedPosts(@Param("limit") int limit, @Param("offset") int offset);
}