package janggi.domain;

public enum Dynasty {
    CHU(0), HAN(1.5), EMPTY(0);

    private final double additionalScore;

    Dynasty(double additionalScore) {
        this.additionalScore = additionalScore;
    }

    public double score(int currentScore) {
        return additionalScore + currentScore;
    }
}
