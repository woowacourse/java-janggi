package janggi.model;

import java.util.Arrays;

public enum Team {
    HAN("한"),
    CHO("초");

    private final String displayName;

    Team(String displayName) {
        this.displayName = displayName;
    }

    public static Team from(String displayName) {
        return Arrays.stream(values())
                .filter(team -> team.displayName.equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 팀입니다."));
    }

    public String getDisplayName() {
        return displayName;
    }
}
