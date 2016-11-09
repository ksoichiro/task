package com.ksoichiro.task.service;

import com.ksoichiro.task.App;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.repository.AccountRepository;
import com.ksoichiro.task.repository.TagRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Sql({"/truncate.sql", "/data-tag.sql"})
@SpringApplicationConfiguration(App.class)
public class TagServiceTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TagService tagService;

    @Test
    public void create() {
        Account account = accountRepository.findOne(1);
        Tag tag = new Tag();
        tag.setAccount(account);
        tag.setName("Private");
        Tag created = tagService.create(tag);
        assertThat(created, is(notNullValue()));
        assertThat(created.getId(), is(greaterThan(1)));
        assertThat(created.getName(), is("Private"));
        assertThat(created.getCreatedAt(), is(notNullValue()));
        assertThat(created.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    public void update() {
        Tag tag = tagRepository.findOne(1);
        tag.setName("Report");
        Tag updated = tagService.update(tag);
        assertThat(updated, is(notNullValue()));
        assertThat(updated.getId(), is(1));
        assertThat(updated.getName(), is("Report"));
        assertThat(updated.getCreatedAt(), is(notNullValue()));
        assertThat(updated.getUpdatedAt(), is(greaterThan(updated.getCreatedAt())));
    }
}
