package domain.piece;

public class EmptyPiece implements Piece {

    @Override
    public boolean canMove() {
        return false;
    }
}
