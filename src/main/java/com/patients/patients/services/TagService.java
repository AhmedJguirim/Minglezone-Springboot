package com.patients.patients.services;

import com.patients.patients.entities.Tag;
import com.patients.patients.exceptions.TagNotFoundException;
import com.patients.patients.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements ITagService{
    @Autowired
    private TagRepository tagRepository;
    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag findTagById(long id) {
        return tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));

    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag updateTag(Tag f, long id) {
        return tagRepository.findById(id)
                .map(tag -> {
                    if (f.getTitle() != null) {
                        tag.setTitle(f.getTitle());
                    }
                    return tagRepository.save(tag);
                })
                .orElseGet(() -> {
                    f.setId(id);
                    return tagRepository.save(f);
                });
    }

    @Override
    public Tag findOneByTagName(String name) {
        return tagRepository.findByTitle(name).get();
    }

    @Override
    public void deleteTag(long id) {
        tagRepository.deleteById(id);
    }
}
