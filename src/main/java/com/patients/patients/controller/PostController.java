package com.patients.patients.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.patients.patients.DTO.CustomUserDetails;
import com.patients.patients.DTO.PostDTO;
import com.patients.patients.entities.Post;
import com.patients.patients.entities.Tag;
import com.patients.patients.entities.User;
import com.patients.patients.exceptions.UserNotFoundException;
import com.patients.patients.repositories.TagRepository;
import com.patients.patients.requestBodies.PostRequest;
import com.patients.patients.services.PostService;
import com.patients.patients.services.UserDetailsServiceImpl;
import com.patients.patients.services.UserService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/posts")
class PostController {

    private final PostService service;

    private final ModelMapper modelMapper;
    private final AuthController authController;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    PostController(PostService service, ModelMapper modelMapper,AuthController authController) {
        this.service = service;
        this.modelMapper = modelMapper;
        this.authController = authController;
    }

    @GetMapping("/")
    List<PostDTO> all(@RequestParam(required = false, defaultValue = "0") int page) {
        return service.findPaginatedPosts(page).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/myPosts")
    List<PostDTO> userPosts() {
        Long userId = authController.getCurrentUserId();
        return service.findAllPostsByOwner(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    PostDTO newPost(@RequestPart("file") MultipartFile file, @RequestParam("content") String content, @RequestParam("tags") List<String> tagTitles) throws IOException {
        Long userId = authController.getCurrentUserId();
        if (!file.isEmpty()) {
            String filePath = saveFileToLocalDirectory(file);
            PostRequest postRequest = new PostRequest(content, filePath, userId);
            Post newPost = createPostEntityFromRequest(postRequest);
            setTagsForPost(newPost, tagTitles);
            Post savedPost = service.createPost(newPost, userId);
            return convertToDTO(savedPost);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
    }

    private String saveFileToLocalDirectory(MultipartFile file) throws IOException {
        String directoryPath = "C:\\Users\\mprix\\Documents\\2eme s2\\springboot proj\\patients\\src\\main\\resources\\static";
        Path path = Paths.get(directoryPath, file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }

    private Post createPostEntityFromRequest(PostRequest request) {
        return new Post(null, request.getContent(), request.getPhoto(), null, new HashSet<>());
    }

    private void setTagsForPost(Post post, List<String> tagTitles) {
        Set<Tag> tags = tagTitles.stream()
                .map(title -> tagRepository.findByTitle(title)
                        .orElseGet(() -> tagRepository.save(new Tag(null, title, new HashSet<>())))
                )
                .collect(Collectors.toSet());
        post.setTags(tags);

    }


    @GetMapping("/{id}")
    PostDTO one(@PathVariable Long id) {
        PostDTO post = convertToDTO(service.findPostById(id));
        return post;
    }

    @PutMapping("/{id}")
    PostDTO replacePost(@RequestBody Post newPost, @PathVariable Long id) throws BadRequestException {
        Long userId = authController.getCurrentUserId();
        Post post = service.findPostById(id);

        if (!post.getOwner().getId().equals(userId)) {
            throw new BadRequestException("You can only update your own posts.");
        }
        return convertToDTO(service.updatePost(newPost, id));
    }

    @DeleteMapping("/{id}")
    void deletePost(@PathVariable Long id) throws BadRequestException {
        Long userId = authController.getCurrentUserId();
        Post post = service.findPostById(id);
        if (!post.getOwner().getId().equals(userId)) {
            throw new BadRequestException("You can only delete your own posts.");
        }
        service.deletePost(id);
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        postDTO.setOwnerId(post.getOwner().getId());
        return postDTO;
    }


}
