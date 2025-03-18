import java.util.List;

public class Horse extends Piece{

    Horse(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if(Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 1) {
            return true;
        }
        if(Math.abs(rowDifference) == 1 && Math.abs(columnDifference) == 2) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        Position route = source.divideBy(destination, 2);

        return List.of(route);
    }
}
