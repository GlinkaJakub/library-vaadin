package com.example.application.data.service;

import com.example.application.data.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {

    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Optional<Tag> get(Integer id) {
        return tagRepository.findById(id);
    }

    public Tag update(Tag entity) {
        return tagRepository.save(entity);
    }

    public void delete(Integer id) {
        tagRepository.deleteById(id);
    }

    public Page<Tag> list(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    public int count() {
        return (int) tagRepository.count();
    }
}
