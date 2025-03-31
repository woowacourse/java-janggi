package janggi.piece.players;

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
        if (this == HAN) {
            return CHO;
        }
        return HAN;
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
