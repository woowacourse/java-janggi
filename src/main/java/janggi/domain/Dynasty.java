package janggi.domain;

public enum Dynasty {
    CHU(72), HAN(73.5), EMPTY(0);

    private final double initialScore;

    Dynasty(double initialScore) {
        this.initialScore = initialScore;
    }
}
