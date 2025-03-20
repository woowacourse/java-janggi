package janggi;

public enum Team {
    GREEN("초"),
    RED("한");

    private final String displayName;

    Team(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return displayName;
    }
}
