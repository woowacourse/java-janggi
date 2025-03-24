package domain.piece;

public enum PieceColor {
    RED("한나라"),
    BLUE("초나라"),
    NONE(""),
    ;

    private final String teamName;

    PieceColor(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}
