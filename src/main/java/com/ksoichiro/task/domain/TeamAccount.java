package com.ksoichiro.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TeamAccount extends BaseEntity {
    private Integer teamId;
    private Integer accountId;

    private TeamAccount() {
    }
}
