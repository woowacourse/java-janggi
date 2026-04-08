package janggi.domain.piece;

import java.util.HashMap;
import java.util.Map;

public enum Team {
    CHO("C"),
    HAN("H"),
    NONE("N");

    private final String code;

    private static final Map<String, Team> BY_CODE = new HashMap<>();

    static {
        for (Team team : values()) {
            BY_CODE.put(team.code, team);
        }
    }

    Team(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Team anotherTeam() {
        if (this == CHO) {
            return HAN;
        }

        if (this == HAN) {
            return CHO;
        }

        throw new IllegalArgumentException("CHO 또는 HAN의 진영을 입력해야 합니다.");
    }

    public static Team fromCode(String code) {
        if (!BY_CODE.containsKey(code)) {
            throw new IllegalArgumentException("알 수 없는 팀 코드입니다: " + code);
        }
        return BY_CODE.get(code);
    }
}
