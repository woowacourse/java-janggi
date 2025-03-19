package domain;

public class Piece {

    private final PieceType pieceType;
    private final Team team;

    Piece(
        final PieceType pieceType,
        final Team team
    ) {
        this.pieceType = pieceType;
        this.team = team;
    }
}
