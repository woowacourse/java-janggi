public class Cannon extends Piece
{

    Cannon(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        return false;
    }
}
