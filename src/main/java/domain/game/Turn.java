package domain.game;

import domain.piece.Team;

import static util.ErrorMessage.NOT_SAME_TEAM;

public enum Turn {

    CHO("초"),
    HAN("한");

    private final String name;

    Turn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Turn reverse() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public void validateSameTeam(Team team) {
        if (!this.name().equals(team.name())) {
            throw new IllegalArgumentException(NOT_SAME_TEAM.getMessage());
        }
    }
}
