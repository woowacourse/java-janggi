package janggi.model;

import java.util.Arrays;

public enum Team {
    HAN("한") {
        @Override
        public Score bonusScore() {
            return new Score(1.5);
        }
    },
    CHO("초") {
        @Override
        public Score bonusScore() {
            return Score.zero();
        }
    };

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

    public abstract Score bonusScore();

    public String getDisplayName() {
        return displayName;
    }
}
