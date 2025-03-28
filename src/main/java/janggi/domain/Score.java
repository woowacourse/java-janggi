package janggi.domain;

public class Score {
    private final double value;

    private Score(double value) {
        validateValue(value);
        this.value = value;
    }

    public static Score from(double totalScore, Team team) {
        if(team == Team.BLUE) {
            return new Score(totalScore);
        }
        return new Score(1.5 + totalScore);
    }

    public Score plus(Score other) {
        return new Score(value + other.value);
    }

    public double getValue() {
        return value;
    }

    private void validateValue(double value) {
        if(value < 0) {
            throw new IllegalArgumentException("점수는 음수가 불가능합니다");
        }
    }


}
