package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.dto.TagDTO;
import com.ksoichiro.task.exception.DuplicateTagNameException;
import com.ksoichiro.task.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TagService {
    private final TagRepository tagRepository;

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
    public Tag create(TagDTO tagDTO) {
        validateName(tagDTO.getName(), tagDTO.getAccount());
        final Tag tag = new Tag();
        BeanUtils.copyProperties(tagDTO, tag);
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag update(TagDTO tagDTO) {
        final Tag toUpdate = tagRepository.findOne(tagDTO.getId());
        if (!tagDTO.getAccount().getId().equals(toUpdate.getAccount().getId())) {
            throw new IllegalStateException("Tag cannot be updated by this account: owner: " + tagDTO.getAccount().getId() + ", updated by: " + toUpdate.getAccount().getId());
        }
        if (tagDTO.getName() != null && !tagDTO.getName().equals(toUpdate.getName())) {
            validateName(tagDTO.getName(), tagDTO.getAccount());
            toUpdate.setName(tagDTO.getName());
        }
        toUpdate.setUpdatedAt(new Date());
        return tagRepository.save(toUpdate);
    }

    void validateName(String name, Account account) {
        final Tag duplicated = tagRepository.findByNameAndAccount(name, account);
        if (duplicated != null) {
            throw new DuplicateTagNameException("Tag name is already registered: " + name + ", id: " + duplicated.getId());
        }
    }
}
