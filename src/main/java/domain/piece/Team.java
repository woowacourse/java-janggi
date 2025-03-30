package domain.piece;

import java.util.Arrays;

public enum Team {

    CHO,
    HAN;

    public Team getEnemy() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public static Team getFirstTeam() {
        return Team.CHO;
    }

    public static Team find(String value) {
        return Arrays.stream(values()).filter(team -> team.toString().equals(value)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));
    }
}
