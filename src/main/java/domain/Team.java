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

    public static Team fromName(final String name) {
        for (Team team : values()) {
            if (team.name.equals(name)) {
                return team;
            }
        }
        throw new IllegalArgumentException("[ERROR] 알 수 없는 팀입니다: " + name);
    }

    public static Team from(final int turnCount) {
        if (turnCount % 2 == 0) {
            return HAN;
        }

        return CHU;
    }
}
