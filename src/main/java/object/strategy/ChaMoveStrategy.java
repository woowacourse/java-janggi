package object.strategy;

import java.util.ArrayList;
import java.util.List;
import object.Coordinate;
import object.Route;

public class ChaMoveStrategy implements MoveStrategy {

    private static final String INVALID_MOVE_LOCATION = "이동불가능한 위치입니다.";

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate) {
        Coordinate minCoordinate = Coordinate.getMinPosition(startCoordinate, endCoordinate);
        Coordinate maxCoordinate = Coordinate.getMaxPosition(startCoordinate, endCoordinate);

        List<Coordinate> coordinates = new ArrayList<>();
        if (startCoordinate.isSameColumn(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(1, 0));
        }
        if (startCoordinate.isSameRow(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(0, 1));
        }
        throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
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
