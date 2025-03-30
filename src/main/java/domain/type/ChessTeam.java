package domain.type;

public enum ChessTeam {

    RED("한나라"),
    BLUE("초나라"),
    ;

    private final String name;

    ChessTeam(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
