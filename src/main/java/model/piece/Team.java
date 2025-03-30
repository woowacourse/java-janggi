package model.piece;


public enum Team {
    RED("레드"),
    GREEN("그린");

    private final String value;

    Team(String value) {
        this.value = value;
    }

    public Team change() {
        if (this == RED) {
            return GREEN;
        }
        return RED;
    }

    public static Team getTeamFromString(String input) {
        return Team.valueOf(input);
    }

    public String getValue() {
        return value;
    }
}
