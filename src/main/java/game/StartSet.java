package game;

public enum StartSet {
    MA_SANG_MA_SANG,
    SANG_MA_SANG_MA,
    SANG_MA_MA_SANG,
    MA_SANG_SANG_MA;

    public static StartSet fromOption(final int i) {
        return values()[i - 1];
    }
}
