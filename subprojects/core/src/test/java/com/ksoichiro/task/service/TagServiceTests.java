package com.ksoichiro.task.service;

import com.ksoichiro.task.App;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.dto.TagDTO;
import com.ksoichiro.task.exception.DuplicateTagNameException;
import com.ksoichiro.task.repository.AccountRepository;
import com.ksoichiro.task.repository.TagRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Sql("/schema-h2.sql")
@Sql({"/truncate.sql", "/data-tag.sql"})
@SpringBootTest(classes = App.class)
public class TagServiceTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TagService tagService;

    @Test
    public void findByAccount() {
        Account account = accountRepository.findOne(1);
        Page<Tag> page = tagRepository.findByAccount(account, new PageRequest(0, 10));
        assertThat(page, is(notNullValue()));
        Tag tag = page.getContent().get(0);
        assertThat(tag, is(notNullValue()));
        assertThat(tag.getName(), is("Work"));
    }

    @Test
    public void create() {
        Account account = accountRepository.findOne(1);
        TagDTO tagDTO = new TagDTO(account);
        tagDTO.setName("Private");
        Tag created = tagService.create(tagDTO);
        assertThat(created, is(notNullValue()));
        assertThat(created.getId(), is(greaterThan(1)));
        assertThat(created.getName(), is("Private"));
        assertThat(created.getCreatedAt(), is(notNullValue()));
        assertThat(created.getUpdatedAt(), is(notNullValue()));
    }

    @Test(expected = DuplicateTagNameException.class)
    public void createWithDuplicateName() {
        Account account = accountRepository.findOne(1);
        TagDTO tagDTO = new TagDTO(account);
        tagDTO.setName("Work");
        tagService.create(tagDTO);
    }

    @Test
    public void update() {
        Account account = accountRepository.findOne(1);
        TagDTO tagDTO = new TagDTO(account);
        tagDTO.setId(1);
        tagDTO.setName("Report");
        Tag updated = tagService.update(tagDTO);
        assertThat(updated, is(notNullValue()));
        assertThat(updated.getId(), is(1));
        assertThat(updated.getName(), is("Report"));
        assertThat(updated.getCreatedAt(), is(notNullValue()));
        assertThat(updated.getUpdatedAt(), is(greaterThan(updated.getCreatedAt())));
    }

    @Test(expected = DuplicateTagNameException.class)
    public void updateWithDuplicateName() {
        Account account = accountRepository.findOne(1);
        TagDTO tagDTO = new TagDTO(account);
        tagDTO.setId(1);
        tagDTO.setName("Hobby");
        tagService.update(tagDTO);
    }

    @Test(expected = IllegalStateException.class)
    public void updateWithDifferentAccount() {
        Account account = accountRepository.findOne(2);
        TagDTO tagDTO = new TagDTO(account);
        tagDTO.setId(1);
        tagDTO.setName("Test");
        tagService.update(tagDTO);
    }
}
