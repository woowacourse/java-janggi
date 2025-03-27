package janggi.setting;

public enum CampType {
    CHO(9, "초", "궁"),
    HAN(0, "한", "궁"),
    ;

    private final int startYPosition;
    private final String name;
    private final String gungName;

    CampType(final int startYPosition, final String name, final String gungName) {
        this.startYPosition = startYPosition;
        this.name = name;
        this.gungName = gungName;
    }

    public int getStartYPosition() {
        return startYPosition;
    }

    public String getName() {
        return name;
    }

    public String getGungName() {
        return gungName;
    }
}
