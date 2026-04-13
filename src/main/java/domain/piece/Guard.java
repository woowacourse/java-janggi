package domain.piece;

public class Guard extends FullPalaceMovementPiece {

    public Guard(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GUARD;
    }
}
