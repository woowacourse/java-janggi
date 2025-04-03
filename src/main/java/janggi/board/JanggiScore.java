package janggi.board;

public record JanggiScore(double value) {

    public JanggiScore plus(final JanggiScore janggiScore) {
        return new JanggiScore(value + janggiScore.value);
    }

    public JanggiScore minus(final JanggiScore janggiScore){
        return new JanggiScore(value - janggiScore.value);
    }

    public boolean isGreaterThan(final JanggiScore scoreOfComparison) {
        return this.value >= scoreOfComparison.value;
    }
}
