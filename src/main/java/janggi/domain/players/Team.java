package janggi.domain.players;

import java.util.Arrays;

public enum Team {

    HAN("한"),
    CHO("초"),
    NONE("해당없음");

    private final String title;

    Team(final String title) {
        this.title = title;
    }

    public Team getOppositeTeam() {
        if (this == CHO) {
            return HAN;
        }
        if (this == HAN) {
            return CHO;
        }
        throw new IllegalStateException("[ERROR] NONE 팀은 반대 팀이 존재하지 않습니다.");
    }

    public static Team from(final String name) {
        return Arrays.stream(values())
                .filter(team -> team.name().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] Team을 찾을 수 없습니다."));
    }

    public String getTitle() {
        return title;
    }
}
