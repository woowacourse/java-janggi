package janggi.team;

public enum TeamName {
    CHO("초"),
    HAN("한");

    private final String name;

    TeamName(String name) {
        this.name = name;
    }

    public boolean matchTeamName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return this.name;
    }
}
