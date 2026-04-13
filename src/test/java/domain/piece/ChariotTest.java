package domain.piece;

class ChariotTest extends DiagonalPalaceMovementPieceTest {


    @Override
    protected DiagonalPalaceMovementPiece createPiece(Team team) {
        return new Chariot(team);
    }
}
