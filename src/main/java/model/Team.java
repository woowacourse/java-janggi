package model;

public enum Team {
    RED,
    GREEN;

    public boolean isMyTurn(Team team) {
        return this.equals(team);
    }

    public Team change() {
        if (this == RED) {
            return GREEN;
        }
        return RED;
    }
}
