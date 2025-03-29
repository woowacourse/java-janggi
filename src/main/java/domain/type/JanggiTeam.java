package domain.type;

public enum JanggiTeam {

    RED("RED"),
    BLUE("BLUE"),
    ;

    public final String name;

    JanggiTeam(String name) {
        this.name = name;
    }

    public static JanggiTeam firstTurn() {
        return BLUE;
    }
}
