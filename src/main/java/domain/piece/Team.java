package domain.piece;

public enum Team {
    CHO(0), HAN(1.5);

    private final double score;

    Team(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public Team opposite() {
        if (this == Team.CHO) {
            return Team.HAN;
        }
        return Team.CHO;
    }
}
