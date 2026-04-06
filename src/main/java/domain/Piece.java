package domain;

public class Piece {

    private final TeamColor teamColor;
    private final PieceType pieceType;

    private Piece(TeamColor teamColor, PieceType pieceType) {
        this.teamColor = teamColor;
        this.pieceType = pieceType;
    }

    public static Piece of(TeamColor teamColor, PieceType pieceType) {
        return new Piece(teamColor, pieceType);
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public TeamColor getTeamColor() {
        return this.teamColor;
    }

    public boolean isOnTeam(TeamColor teamColor) {
        return this.teamColor == teamColor;
    }

    public MaterialPoints materialPoints() {
        return pieceType.materialPoints();
    }

    public boolean isKing() {
        return this.pieceType == PieceType.KING;
    }
}
