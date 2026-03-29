package domain.vo;

public enum Team {
    CHO("초(CHO)", "초"),
    HAN("한(HAN)", "한");

    private final String teamName;
    private final String prefix;

    Team(String teamName, String prefix) {
        this.teamName = teamName;
        this.prefix = prefix;
    }

    public String display() {
        return teamName;
    }

    public String displayPiece(PieceType pieceType) {
        return prefix + pieceType.display();
    }

    public String colorCode(String red, String green) {
        if (this == HAN) {
            return red;
        }
        return green;
    }

    public Team enemy() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
