package domain.piece;

public class General extends FullPalaceMovementPiece {

    public General(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GENERAL;
    }
}
