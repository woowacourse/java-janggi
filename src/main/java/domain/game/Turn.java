package domain.game;

import domain.piece.Team;

public enum Turn {

    CHO("초"),
    HAN("한");

    private static final String NOT_SAME_TEAM = "본인의 진영의 기물이 아닙니다.";

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
            throw new IllegalArgumentException(NOT_SAME_TEAM);
        }
    }
}
