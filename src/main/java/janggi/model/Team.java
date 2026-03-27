package janggi.model;

public enum Team {
    HAN("초"),
    CHO("한");

    private final String displayName;

    Team(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
