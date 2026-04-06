package domain.piece;

public enum Team {
    CHO("초(CHO)", "초", 0.0),
    HAN("한(HAN)", "한", 1.5);

    private final String teamName;
    private final String prefix;
    private final double dumPoint;

    Team(String teamName, String prefix, double dumPoint) {
        this.teamName = teamName;
        this.prefix = prefix;
        this.dumPoint = dumPoint;
    }

    public double dumPoint() {
        return dumPoint;
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
