package model;

public enum Team {
    RED("레드"),
    GREEN("그린");

    private final String value;

    Team(String value) {
        this.value = value;
    }

    public boolean isMyTurn(Team team) {
        return this.equals(team);
    }

    public Team change() {
        if (this == RED) {
            return GREEN;
        }
        return RED;
    }

    public String getValue() {
        return value;
    }
}
