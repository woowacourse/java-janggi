package domain;

public enum TeamColor {
    CHO("초"),
    HAN("한");

    private final String displayName;

    TeamColor(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public MaterialPoints startingScore() {
        if (this == CHO) {
            return MaterialPoints.of(72);
        }
        return MaterialPoints.of(73.5);
    }
}
