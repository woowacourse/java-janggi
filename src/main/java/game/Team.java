package game;

import java.util.Arrays;

public enum Team {
    RED(73.5),
    GREEN(72),
    NONE(0);

    private final double initialScore;

    Team(double initialScore) {
        this.initialScore = initialScore;
    }

    public static Team fromName(String teamName) {
        return Arrays.stream(Team.values())
                .filter(team -> team.name().equalsIgnoreCase(teamName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당 이름의 팀이 존재하지 않습니다."));
    }

    public Team findOpponent() {
        if (this == RED) {
            return GREEN;
        }
        if (this == GREEN) {
            return RED;
        }
        throw new IllegalStateException("[ERROR] 유효하지 않은 팀입니다.");
    }

    public double calculateFinalScore(int catchScore) {
        return initialScore - catchScore;
    }

    public boolean isNotDecided() {
        return this == NONE;
    }
}
