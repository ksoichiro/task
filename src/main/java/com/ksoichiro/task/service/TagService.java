package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Page<Tag> findByAccount(Account account, Pageable pageable) {
        return tagRepository.findByAccount(account, pageable);
    }

    @Transactional
    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }
}
