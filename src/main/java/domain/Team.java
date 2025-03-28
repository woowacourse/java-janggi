package domain;

public enum Team {
    RED,
    GREEN;

    public boolean isGreenTeam() {
        return GREEN == this;
    }

    public boolean isRedTeam() {
        return RED == this;
    }

    public Team opposite() {
        if (this.isGreenTeam()) {
            return Team.RED;
        }
        return Team.GREEN;
    }
}
