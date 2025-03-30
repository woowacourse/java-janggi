package model;

public enum Team {
    RED("한", 1.5, 7, 9),
    BLUE("초", 0, 0, 2);


    private final String team;
    private final double defaultScore;
    private final int yPalaceMinimum;
    private final int yPalaceMaximum;

    Team(String team, double defaultScore, int yPalaceMinimum, int yPalaceMaximum) {
        this.team = team;
        this.defaultScore = defaultScore;
        this.yPalaceMinimum = yPalaceMinimum;
        this.yPalaceMaximum = yPalaceMaximum;
    }

    public boolean isRed() {
        return this == RED;
    }

    public String getTeam() {
        return team;
    }

    public double getDefaultScore() {
        return defaultScore;
    }

    public int getYPalaceMinimum() {
        return yPalaceMinimum;
    }

    public int getYPalaceMaximum() {
        return yPalaceMaximum;
    }

    public static Team findTeamByName(String teamName) {
        for (Team team : Team.values()) {
            if (team.getTeam().equals(teamName)) {
                return team;
            }
        }
        return null;
    }
}
