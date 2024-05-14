package com.patients.patients.services;

import com.patients.patients.entities.Post;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPostService {
    public Post createPost(Post post, long ownerId);
    public Post findPostById(long id);
    public List<Post> findAllPosts();
    List<Post> findPaginatedPosts(int page);
    public List<Post> findAllPostsByOwner(long ownerId);

    public Post updatePost(Post newPost , long id);
    public void deletePost(long id);
}
