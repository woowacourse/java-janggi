package domain.player;

import java.util.Arrays;

public enum Team {
    HAN("한", 1.5F),
    CHO("초", 0F),
    ;

    private final String name;
    private final float defaultScore;

    Team(String name, float defaultScore) {
        this.name = name;
        this.defaultScore = defaultScore;
    }

    public static Team getValue(final String teamName) {
        return Arrays.stream(values())
                .filter(team -> team.name().equals(teamName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 팀 값입니다."));
    }

    public static Team getOtherTeam(final Team team) {
        if (team == HAN) {
            return CHO;
        }
        return HAN;
    }

    public String getName() {
        return name;
    }

    public float getDefaultScore() {
        return defaultScore;
    }
}
