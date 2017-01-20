package com.ksoichiro.task.form;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Team;
import com.ksoichiro.task.dto.TeamDTO;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

@Data
public class TeamCreateForm implements CreateForm<Team, TeamDTO> {
    @NotEmpty
    private String cd;

    @NotEmpty
    private String name;

    @Override
    public TeamDTO toDTO(Account account) {
        final TeamDTO teamDTO = new TeamDTO();
        BeanUtils.copyProperties(this, teamDTO);
        teamDTO.setAccount(account);
        return teamDTO;
    }
}
