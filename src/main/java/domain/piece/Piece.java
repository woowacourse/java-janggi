package domain.piece;

public class Piece {
    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public String display() {
        return team.displayPiece(pieceType);
    }

    public String colorCode(String red, String green) {
        return team.colorCode(red, green);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isChariot() {
        return pieceType == PieceType.CHARIOT;
    }

    public boolean isCannon() {
        return pieceType == PieceType.CANNON;
    }

    public boolean isLeapPiece() {
        return pieceType == PieceType.HORSE || pieceType == PieceType.ELEPHANT;
    }

    public boolean isSoldier() {
        return pieceType == PieceType.SOLDIER;
    }

    public boolean isGeneral() {
        return pieceType == PieceType.GENERAL;
    }

    public boolean isGuard() {
        return pieceType == PieceType.GUARD;
    }

    public boolean isPalacePiece() {
        return pieceType == PieceType.GENERAL || pieceType == PieceType.GUARD;
    }

    public int score() {
        return pieceType.score();
    }

    public Team getTeam() {
        return team;
    }

    public boolean isOwnedBy(Team targetTeam) {
        return this.team == targetTeam;
    }

    public boolean isSameTeamAs(Piece other) {
        return this.team == other.team;
    }
}
