package janggi.domain;

public enum Team {
    CHO("초"),
    HAN("한");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public Team switchTeam() {
        if (this == Team.CHO) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    public String getTeam() {
        return name;
    }

    public String getName() {
        return name;
    }
}
