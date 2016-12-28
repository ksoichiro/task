package com.ksoichiro.task.dto;

import com.ksoichiro.task.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagDTO {
    private Integer id;
    private String name;
    private Account account;

    public TagDTO(Account account) {
        this.account = account;
    }
}
