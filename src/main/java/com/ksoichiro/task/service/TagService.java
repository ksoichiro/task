package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.exception.DuplicateTagNameException;
import com.ksoichiro.task.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Page<Tag> findByAccount(Account account, Pageable pageable) {
        return tagRepository.findByAccount(account, pageable);
    }

    public List<Tag> findByAccount(Account account) {
        return tagRepository.findByAccount(account);
    }

    public Tag findByIdAndAccount(Integer id, Account account) {
        return tagRepository.findByIdAndAccount(id, account);
    }

    @Transactional
    public Tag create(Tag tag) {
        validateName(tag);
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag update(Tag tag) {
        Tag toUpdate = tagRepository.findOne(tag.getId());
        if (!tag.getAccount().getId().equals(toUpdate.getAccount().getId())) {
            throw new IllegalStateException("Tag cannot be updated by this account: owner: " + tag.getAccount().getId() + ", updated by: " + toUpdate.getAccount().getId());
        }
        if (tag.getName() != null && !tag.getName().equals(toUpdate.getName())) {
            toUpdate.setName(tag.getName());
            validateName(toUpdate);
        }
        toUpdate.setUpdatedAt(new Date());
        return tagRepository.save(toUpdate);
    }

    void validateName(Tag tag) {
        if (tagRepository.findByNameAndAccount(tag.getName(), tag.getAccount()) != null) {
            throw new DuplicateTagNameException("Tag name is already registered: " + tag.getName());
        }
    }
}
