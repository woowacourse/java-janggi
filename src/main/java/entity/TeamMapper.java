package entity;

import domain.Team;

public class TeamMapper {
    public static Team toTeam(TeamEntity teamEntity) {
        if (teamEntity.getName().equals(Team.CHO.name())) {
            return Team.CHO;
        }
        return Team.HAN;
    }
}
