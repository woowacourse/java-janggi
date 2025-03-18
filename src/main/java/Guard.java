public class Guard extends Piece{

    Guard(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        return false;
    }
}
