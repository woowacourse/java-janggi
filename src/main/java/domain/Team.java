package domain;

public enum Team {
    CHO("초",0.0),
    HAN("한", 1.5),
    NONE("없음", 0.0);

    private final String name;
    private final Double bonusScore;

    Team(String name, Double bonusScore) {
        this.name = name;
        this.bonusScore = bonusScore;
    }

    public String getName() {
        return name;
    }

    public Double getBonusScore() {
        return bonusScore;
    }
}
