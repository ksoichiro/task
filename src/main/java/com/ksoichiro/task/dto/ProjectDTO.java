package com.ksoichiro.task.dto;

import com.ksoichiro.task.domain.Account;
import lombok.Data;

@Data
public class ProjectDTO {
    private Integer id;
    private String cd;
    private String name;
    private Integer teamId;
    private Account account;
}
