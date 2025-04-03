package janggi.domain;

public class TotalScore {

    private final double value;

    private TotalScore(double value) {
        validateValue(value);
        this.value = value;
    }

    public static TotalScore from(double totalScore, Team team) {
        if (team == Turn.First().getTeam()) {
            return new TotalScore(totalScore);
        }
        return new TotalScore(1.5 + totalScore);
    }

    public double getValue() {
        return value;
    }

    private void validateValue(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("점수는 음수가 불가능합니다");
        }
    }
}
