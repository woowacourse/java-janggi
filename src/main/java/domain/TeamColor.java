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
}
