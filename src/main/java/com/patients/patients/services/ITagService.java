package com.patients.patients.services;

import com.patients.patients.entities.Tag;

import java.util.List;

public interface ITagService {
    public Tag createTag(Tag tag);
    public Tag findTagById(long id);
    public List<Tag> findAllTags();
    public Tag updateTag(Tag f, long id);
    Tag findOneByTagName(String name);
    public void deleteTag(long id);

}
