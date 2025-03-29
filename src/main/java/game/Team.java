package game;

public enum Team {
    RED(73.5),
    GREEN(72),
    NONE(0);

    private final double initialScore;

    Team(double initialScore) {
        this.initialScore = initialScore;
    }

    public double getInitialScore() {
        return initialScore;
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

    public boolean isNotDecided() {
        return this == NONE;
    }
}
