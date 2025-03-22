package model;

public enum Team {
    RED("한"),
    BLUE("초");


    private final String team;

    Team(String team) {
        this.team = team;
    }

    public boolean isRed() {
        return this == RED;
    }

    public String getTeam() {
        return team;
    }
}
