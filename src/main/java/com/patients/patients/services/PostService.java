package com.patients.patients.services;

import com.patients.patients.entities.Post;
import com.patients.patients.entities.User;
import com.patients.patients.exceptions.PostNotFoundException;
import com.patients.patients.exceptions.UserNotFoundException;
import com.patients.patients.repositories.PostRepository;
import com.patients.patients.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostService implements IPostService{

   @Autowired
    private PostRepository postRepository;
   @Autowired
   UserRepository userRepository;
    @Override
    public Post createPost(Post post, long ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException(ownerId));
        post.setOwner(owner);
        return postRepository.save(post);
    }

    @Override
    public Post findPostById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }
    @Override
    public List<Post> findPaginatedPosts(int page){
        int pageSize = 2;
        int offset = pageSize * page;
        return postRepository.findPaginatedPosts(pageSize, offset);
    }

    @Override
    public List<Post> findAllPostsByOwner(long ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException(ownerId));
        return postRepository.findByOwner(owner);
    }

    @Override
    public Post updatePost(Post newPost , long id) {
        return postRepository.findById(id)
                .map(post -> {
                    if (newPost.getPhoto() != null) {
                        post.setPhoto(newPost.getPhoto());
                    }
                    if (newPost.getContent() != null) {
                        post.setContent(newPost.getContent());
                    }
                    return postRepository.save(post);
                })
                .orElseGet(() -> {
                    newPost.setId(id);
                    return postRepository.save(newPost);
                });
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
