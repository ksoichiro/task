package com.ksoichiro.task.form;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Team;
import com.ksoichiro.task.dto.TeamDTO;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
public class TeamUpdateForm implements UpdateForm<Team, TeamDTO> {
    @NotNull
    @Getter(onMethod = @__({@Override}))
    private Integer id;

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
