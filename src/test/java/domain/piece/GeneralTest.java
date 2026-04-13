package domain.piece;

class GeneralTest extends FullPalaceMovementPieceTest {

    @Override
    public FullPalaceMovementPiece createPiece(Team team) {
        return new General(team);
    }
}
