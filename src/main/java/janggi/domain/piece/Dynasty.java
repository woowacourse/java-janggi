package janggi.domain.piece;

public enum Dynasty {
    CHU(0), HAN(1.5), EMPTY(0);

    private final double additionalScore;

    Dynasty(double additionalScore) {
        this.additionalScore = additionalScore;
    }

    public double score(int currentScore) {
        return additionalScore + currentScore;
    }

    public Dynasty opposite() {
        if (this == CHU) {
            return HAN;
        }
        if (this == HAN) {
            return CHU;
        }
        return EMPTY;
    }
}
