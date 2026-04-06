package janggi.domain.side;

public enum Side {
    HAN("한", 1.5),
    CHO("초", 0),
    NONE("  ", 0);

    private final String name;
    private final double bonusScore;

    Side(String name, double bonusScore) {
        this.name = name;
        this.bonusScore = bonusScore;
    }

    public static boolean isSameSide(Side firstSide, Side secondSide) {
        return firstSide.equals(secondSide);
    }

    public String getName() {
        return name;
    }

    public double getBonusScore() {
        return bonusScore;
    }
}
