package com.patients.patients.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.patients.patients.DTO.PostDTO;
import com.patients.patients.DTO.TagDTO;
import com.patients.patients.entities.Post;
import com.patients.patients.entities.Tag;
import com.patients.patients.exceptions.TagNotFoundException;
import com.patients.patients.repositories.TagRepository;
import com.patients.patients.services.PostService;
import com.patients.patients.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
class TagController {

    private final TagService service;

    private final ModelMapper modelMapper;

    @Autowired
    TagController(TagService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/tags")
    List<TagDTO> all() {
        return service.findAllTags().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // end::get-aggregate-root[]

    @PostMapping("/tags")
    TagDTO newTag(@RequestBody Tag newTag) {
        return convertToDTO(service.createTag(newTag));
    }

    // Single item

    @GetMapping("/tags/{id}")
    TagDTO one(@PathVariable Long id) {

        try {
            return convertToDTO(service.findTagById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/tags/{id}")
    TagDTO replaceTag(@RequestBody Tag newTag, @PathVariable Long id) {

        return convertToDTO(service.updateTag(newTag,id));
    }
    @GetMapping("/tags/search")
    TagDTO oneByName(@RequestParam("name") String name) {
        try {
                return convertToDTO(service.findOneByTagName(name));
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    @DeleteMapping("/tags/{id}")
    void deleteTag(@PathVariable Long id) {
        service.deleteTag(id);
    }
    private TagDTO convertToDTO(Tag tag) {
        TagDTO tagDTO = modelMapper.map(tag, TagDTO.class);
        return tagDTO;
    }
}
