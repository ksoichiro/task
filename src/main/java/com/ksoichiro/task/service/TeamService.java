package com.ksoichiro.task.service;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Team;
import com.ksoichiro.task.domain.TeamAccount;
import com.ksoichiro.task.dto.TeamDTO;
import com.ksoichiro.task.repository.TeamAccountRepository;
import com.ksoichiro.task.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TeamService {
    private TeamRepository teamRepository;

    private TeamAccountRepository teamAccountRepository;

    public List<Team> findByAccount(Account account) {
        return teamRepository.findByAccount(account);
    }

    public Page<Team> findByAccount(Account account, Pageable pageable) {
        return teamRepository.findByAccount(account, pageable);
    }

    @Transactional
    public Team create(TeamDTO teamDTO) {
        if (teamDTO == null) {
            throw new IllegalArgumentException("teamDTO cannot be null");
        }
        Team team = new Team();
        BeanUtils.copyProperties(teamDTO, team, "id");
        List<TeamAccount> teamAccounts = new ArrayList<>();
        team = teamRepository.save(team);
        TeamAccount teamAccount = new TeamAccount();
        teamAccount.setAccount(teamDTO.getAccount());
        teamAccount.setTeam(team);
        teamAccounts.add(teamAccount);
        teamAccounts = teamAccountRepository.save(teamAccounts);
        team.setTeamAccounts(teamAccounts);
        return team;
    }

    @Transactional
    public Team update(TeamDTO teamDTO) {
        if (teamDTO == null || teamDTO.getId() == null) {
            throw new IllegalArgumentException("teamDTO and id cannot be null");
        }
        Team team = teamRepository.findOne(teamDTO.getId());
        team.setUpdatedAt(new Date());
        return teamRepository.save(team);
    }
}
