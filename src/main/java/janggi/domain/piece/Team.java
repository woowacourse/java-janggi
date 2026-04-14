package janggi.domain.piece;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Team {
    CHO("C"),
    HAN("H"),
    NONE("N");

    private final String code;

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
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 팀 코드입니다: " + code));
    }
}
