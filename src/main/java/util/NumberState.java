package util;

public enum NumberState {
    POSITIVE,
    NEGATIVE,
    ZERO;

    public static NumberState findNumberState(int value) {
        if (value < 0) {
            return NEGATIVE;
        }

        if (value > 0) {
            return POSITIVE;
        }

        return ZERO;
    }
}
