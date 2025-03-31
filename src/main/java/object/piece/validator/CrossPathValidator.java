package object.piece.validator;

import object.board.Board;
import object.coordinate.Coordinate;
import java.util.HashSet;
import java.util.Set;

public class CrossPathValidator implements PathValidator {

    @Override
    public boolean validate(Board board, Coordinate departure, Coordinate arrival) {
        return findPaths(departure, arrival)
                .stream()
                .noneMatch(board::hasPiece);
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
