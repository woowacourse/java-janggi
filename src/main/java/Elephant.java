import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece{

    Elephant(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if(Math.abs(rowDifference) == 3 && Math.abs(columnDifference) == 2) {
            return true;
        }
        if(Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 3) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        List<Position> positions = new ArrayList<>();

        Position firstPosition = source.divideBy(destination, 3);
        Position secondPosition = firstPosition.divideBy(destination, 2);

        positions.add(firstPosition);
        positions.add(secondPosition);
        return positions;
    }
}
