package domain;

public enum Team {
    CHU("초"),
    HAN("한");

    private final String name;

    Team(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Team from(final int turnCount) {
        if (turnCount % 2 == 0) {
            return HAN;
        }

        return CHU;
    }
}
