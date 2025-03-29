package game;

public enum Team {
    RED(73.5),
    GREEN(72),
    NONE(0);

    private final double initialScore;

    Team(double initialScore) {
        this.initialScore = initialScore;
    }

    public static Team findOpponentBy(Team team) {
        if(team == RED) {
            return GREEN;
        }
        if(team == GREEN) {
            return RED;
        }
        throw new IllegalStateException("[ERROR] 유효하지 않은 팀입니다.");
    }

    public static Team findLatter() {
        return Team.RED;
    }

    public double getInitialScore() {
        return initialScore;
    }

    public boolean isNotDecided() {
        return this == NONE;
    }
}
