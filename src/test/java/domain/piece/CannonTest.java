package domain.piece;

class CannonTest extends DiagonalPalaceMovementPieceTest {


    @Override
    protected DiagonalPalaceMovementPiece createPiece(Team team) {
        return new Cannon(team);
    }
}
