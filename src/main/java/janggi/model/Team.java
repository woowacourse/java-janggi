package janggi.model;

public enum Team {
    HAN("한"),
    CHO("초");

    private final String displayName;

    Team(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
