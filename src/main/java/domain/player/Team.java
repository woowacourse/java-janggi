package domain.player;

public enum Team {
    HAN("한", 1.5F),
    CHO("초", 0F),
    ;

    private final String name;
    private final float defaultScore;

    Team(String name, float defaultScore) {
        this.name = name;
        this.defaultScore = defaultScore;
    }

    public String getName() {
        return name;
    }

    public float getDefaultScore() {
        return defaultScore;
    }
}
