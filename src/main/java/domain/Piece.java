package domain;

public class Piece {
    private TeamColor teamColor;
    private PieceType pieceType;

    private Piece(TeamColor teamColor, PieceType pieceType) {
        this.teamColor = teamColor;
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public TeamColor getTeamColor() {
        return this.teamColor;
    }

    public static Piece of(TeamColor teamColor, PieceType pieceType) {
        return new Piece(teamColor,pieceType);
    }
}
