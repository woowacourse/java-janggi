package domain.game;

public enum Turn {

    CHO("초"),
    HAN("한");

    private final String name;

    Turn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Turn reverse() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
