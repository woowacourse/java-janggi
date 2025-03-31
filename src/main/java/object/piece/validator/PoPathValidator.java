package object.piece.validator;

import object.board.Board;
import object.coordinate.Coordinate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PoPathValidator implements PathValidator {

    @Override
    public boolean validate(Board board, Coordinate departure, Coordinate arrival) {
        List<Coordinate> obstacles = findPaths(departure, arrival).stream()
                .filter(board::hasPiece)
                .toList();

        if (obstacles.size() != 1) {
            return false;
        }
        if (board.findPiece(obstacles.getFirst()).isPo()) {
            return false;
        }
        if (board.hasPiece(arrival) && board.findPiece(arrival).isPo()) {
            return false;
        }
        return true;
    }

    private Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int xDirection = Integer.compare(arrival.getX(), departure.getX());
        int yDirection = Integer.compare(arrival.getY(), departure.getY());

        Set<Coordinate> coordinates = new HashSet<>();
        int x = departure.getX() + xDirection;
        int y = departure.getY() + yDirection;

        while (x != arrival.getX() || y != arrival.getY()) {
            coordinates.add(new Coordinate(x, y));
            x += xDirection;
            y += yDirection;
        }

        return coordinates;
    }
}
