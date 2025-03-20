package object.strategy;

import java.util.ArrayList;
import java.util.List;
import object.piece.Piece;
import object.piece.Pieces;
import object.Coordinate;
import object.Route;
import object.piece.Team;

public interface MoveStrategy {

    String INVALID_POSITION = "도달할 수 없는 위치입니다.";

    Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team);

    default Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, List<Route> canMoveDirections) {
        for (Route canMoveDirection : canMoveDirections) {
            List<Coordinate> moveRoute = new ArrayList<>();
            Coordinate currentCoordinate = startCoordinate;
            currentCoordinate = movePosition(canMoveDirection, currentCoordinate, moveRoute);
            if (currentCoordinate.equals(endCoordinate)) {
                return new Route(moveRoute);
            }
        }
        throw new IllegalArgumentException(INVALID_POSITION);
    }

    default Coordinate move(Coordinate destination, Pieces onRoutePieces, Team moveTeam) {
        for (Piece piece : onRoutePieces.getPieces()) {
            if (!piece.isSamePosition(destination)) {
                throw new IllegalArgumentException(INVALID_POSITION);
            }
            if (piece.isSameTeam(moveTeam)) {
                throw new IllegalArgumentException(INVALID_POSITION);
            }
        }

        return destination;
    }

    private Coordinate movePosition(Route canMoveDirection, Coordinate currentCoordinate, List<Coordinate> moveRoute) {
        for (Coordinate coordinate : canMoveDirection.getPositions()) {
            currentCoordinate = currentCoordinate.add(coordinate);
            moveRoute.add(currentCoordinate);
        }
        return currentCoordinate;
    }
}
