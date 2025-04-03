package entity;

import domain.type.JanggiTeam;

public record TurnEntity(
        JanggiTeam team
) {
    public String getTeamName() {
        return team.name;
    }
}
