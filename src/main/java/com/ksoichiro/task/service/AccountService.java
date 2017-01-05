package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AccountService {
    private final AccountRepository accountRepository;

    public Account findById(Integer id) {
        return accountRepository.findOne(id);
    }

    @Transactional
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    public Account update(Account account) {
        account.setUpdatedAt(new Date());
        return accountRepository.save(account);
    }
}
