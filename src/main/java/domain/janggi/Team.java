package domain.janggi;

import java.util.Arrays;

public enum Team {
    GREEN("초나라"),
    RED("한나라");

    private final String title;

    Team(final String title) {
        this.title = title;
    }

    public Team opposite() {
        if (this == GREEN) {
            return RED;
        }

        return GREEN;
    }

    public static Team from(final String name) {
        return Arrays.stream(Team.values())
                .filter(team -> team.name().equals(name.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 팀이 없습니다."));
    }

    public String getTitle() {
        return title;
    }
}
