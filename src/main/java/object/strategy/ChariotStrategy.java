package object.strategy;

import java.util.ArrayList;
import java.util.List;
import object.Coordinate;
import object.Route;
import object.piece.Team;

public class ChariotStrategy implements MoveStrategy {

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        Coordinate minCoordinate = Coordinate.getMinPosition(startCoordinate, endCoordinate);
        Coordinate maxCoordinate = Coordinate.getMaxPosition(startCoordinate, endCoordinate);

        List<Coordinate> coordinates = new ArrayList<>();
        if (startCoordinate.isSameColumn(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(1, 0));
        }
        if (startCoordinate.isSameRow(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(0, 1));
        }
        throw new IllegalArgumentException(MoveStrategy.INVALID_POSITION);
    }

    private static Route calculateLegalRoute(Coordinate minCoordinate, Coordinate maxCoordinate, List<Coordinate> coordinates,
                                             Coordinate direction) {
        while (!minCoordinate.equals(maxCoordinate)) {
            minCoordinate = minCoordinate.add(direction);
            coordinates.add(minCoordinate);
        }
        return new Route(coordinates);
    }
}
