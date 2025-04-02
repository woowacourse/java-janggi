package janggi.piece;

import java.util.Arrays;

public enum Team {

    HAN("한나라(RED)") {
        @Override
        public Team changeTeam() {
            return CHU;
        }
    },
    CHU("초나라(GREEN)") {
        @Override
        public Team changeTeam() {
            return HAN;
        }
    };

    private final String description;

    Team(final String description) {
        this.description = description;
    }

    public static Team from(final String value) {
        return Arrays.stream(values())
                .filter(team -> team.description.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 일치하는 팀이 없습니다."));
    }

    public abstract Team changeTeam();

    public static boolean isChu(final Team team) {
        return CHU == team;
    }

    public static boolean isHan(final Team team) {
        return HAN == team;
    }

    public boolean isSameTeam(final Team team) {
        return this == team;
    }

    public String getDescription() {
        return description;
    }
}
