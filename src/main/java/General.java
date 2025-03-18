import java.util.List;

public class General extends Piece{

    General(PieceColor color) {
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
