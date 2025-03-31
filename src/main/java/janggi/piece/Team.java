package janggi.piece;

import janggi.position.Direction;
import java.util.Arrays;
import java.util.List;

public enum Team {
    HAN("한나라"),
    CHO("초나라"),
    ;

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public Team getOpposite() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public List<Direction> getTeamDirection() {
        if (this == CHO) {
            return Direction.getBackDirection();
        }
        return Direction.getFrontDirection();
    }

    public String getName() {
        return name;
    }

    public static Team fromString(String value) {
        return Arrays.stream(values())
                .filter(team -> team.name.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("팀 정보가 없습니다."));
    }
}
