package janggi;

public enum Team {
    GREEN("초"),
    RED("한");

    private final String displayName;

    Team(String displayName) {
        this.displayName = displayName;
    }

    public Team convertTeam() {
        if (this == GREEN) {
            return RED;
        }
        return GREEN;
    }

    public String getDisplayName() {
        return displayName;
    }
}
