package com.ksoichiro.task.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TeamDTOTests {
    @Data
    @EqualsAndHashCode(callSuper = true)
    static class TeamDTO2 extends TeamDTO {
    }

    @Test
    @SuppressWarnings("all")
    public void testEquals() {
        TeamDTO dto = new TeamDTO();
        assertThat(dto.equals(dto), is(true));
        assertThat(dto.equals(null), is(false));

        assertThat(dto.equals("test"), is(false));
        dto.hashCode();
    }

    @Test
    @SuppressWarnings("all")
    public void testToString() {
        TeamDTO teamDTO = new TeamDTO();
        assertThat(teamDTO, is(notNullValue()));
    }

    @Test
    @SuppressWarnings("all")
    public void testHashCode() {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.hashCode();
    }
}
