package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LoginService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
        Account domainAccount = accountRepository.findByUsername(username);
        if (domainAccount == null) {
            throw new UsernameNotFoundException("User not found for name: " + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (domainAccount.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(domainAccount.getRole().getName()));
        }
        Account account = new Account(username, domainAccount.getPassword(), domainAccount.isEnabled(), authorities);
        account.setId(domainAccount.getId());
        account.setName(domainAccount.getName());
        account.setCreatedAt(domainAccount.getCreatedAt());
        account.setUpdatedAt(domainAccount.getUpdatedAt());
        account.setRole(domainAccount.getRole());
        return account;
    }
}
