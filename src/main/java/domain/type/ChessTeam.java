package domain.type;

public enum ChessTeam {

    RED,
    BLUE,
    ;

    public static ChessTeam firstTurn() {
        return BLUE;
    }
}
