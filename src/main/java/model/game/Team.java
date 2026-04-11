package model.game;

import java.util.stream.Stream;

public enum Team {
    HAN("한나라"), CHO("초나라");

    private final String koreanName;

    Team(String koreanName) {
        this.koreanName = koreanName;
    }

    public static Team fromName(String name) {
        return Stream.of(values())
                .filter(team -> team.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀명입니다."));
    }

    public String getKoreanName() {
        return koreanName;
    }

    public Team next() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
