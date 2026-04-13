package domain.game;

import domain.piece.Team;

import java.util.Arrays;

public enum Turn {

    CHO("초"),
    HAN("한");

    private static final String NOT_SAME_TEAM_MESSAGE = "본인의 진영의 기물이 아닙니다.";
    private static final String NOT_EXIST_TURN = "존재하지 않는 턴입니다: ";

    private final String name;

    Turn(String name) {
        this.name = name;
    }

    public static Turn from(String name) {
        return Arrays.stream(values())
                .filter(turn -> turn.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_TURN + name));
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
            throw new IllegalArgumentException(NOT_SAME_TEAM_MESSAGE);
        }
    }
}
