package janggi.dto;

import janggi.domain.piece.Team;

import java.util.EnumMap;
import java.util.Map;

public class TeamInputDto {
    private static final Map<Team, String> mapper;

    static {
        mapper = new EnumMap<>(Team.class);
        mapper.put(Team.CHO, "초");
        mapper.put(Team.HAN, "한");
    }

    private final String teamName;

    public TeamInputDto(Team team) {
        this.teamName = mapper.get(team);
    }

    public String getTeamName() {
        return teamName;
    }
}
