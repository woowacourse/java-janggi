package janggi.rule;

public enum CampType {
    CHO("초", 9, 0d),
    HAN("한", 0, 0.5d);

    private final String name;
    private final int startYPosition;
    private final double defaultScore;

    CampType(String name, int startYPosition, double defaultScore) {
        this.name = name;
        this.startYPosition = startYPosition;
        this.defaultScore = defaultScore;
    }

    public CampType getEnemyCampType() {
        if (this == CampType.CHO) {
            return CampType.HAN;
        }
        return CampType.CHO;
    }

    public double addCampDefaultScore(double originScore) {
        return originScore + this.getDefaultScore();
    }

    public int getStartYPosition() {
        return startYPosition;
    }

    public String getName() {
        return name;
    }

    public double getDefaultScore() {
        return defaultScore;
    }
}
