package domain;

public enum TeamColor {
    CHO("초", 72),
    HAN("한", 73.5);

    private final String displayName;
    private final MaterialPoints startingScore;

    TeamColor(String displayName, double startingScoreValue) {
        this.displayName = displayName;
        this.startingScore = MaterialPoints.of(startingScoreValue);
    }

    public String getDisplayName() {
        return displayName;
    }

    public MaterialPoints startingScore() {
        return startingScore;
    }
}
