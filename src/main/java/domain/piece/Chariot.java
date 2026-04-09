package domain.piece;

public class Chariot extends DiagonalPalaceMovementPiece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CHARIOT;
    }
}
