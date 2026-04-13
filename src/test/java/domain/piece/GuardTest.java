package domain.piece;

class GuardTest extends FullPalaceMovementPieceTest {

    @Override
    public FullPalaceMovementPiece createPiece(Team team) {
        return new Guard(team);
    }
}
