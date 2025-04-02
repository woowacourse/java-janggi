package janggi.domain.team;

import java.util.Arrays;

public enum TeamType {

    HAN("한"),
    CHO("초");

    private final String title;

    TeamType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static TeamType of(String title) {
        return Arrays.stream(values())
                .filter(teamType -> teamType.title.equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 팀은 존재하지 않습니다."));
    }
}
