package janggi.setting;

public enum CampType {
    CHO(9, "초"),
    HAN(0, "한"),
    ;

    private final int startYPosition;
    private final String name;

    CampType(final int startYPosition, final String name) {
        this.startYPosition = startYPosition;
        this.name = name;
    }

    public int getStartYPosition() {
        return startYPosition;
    }

    public String getName() {
        return name;
    }
}
