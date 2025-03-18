import java.util.List;

public class Guard extends Piece{

    Guard(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return List.of();
    }
}
