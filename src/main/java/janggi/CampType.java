package janggi;

public enum CampType {
    CHO(0),
    HAN(9),
    ;

    private final int startYPosition;

    CampType(final int startYPosition) {
        this.startYPosition = startYPosition;
    }

    public int getStartYPosition() {
        return startYPosition;
    }
}
