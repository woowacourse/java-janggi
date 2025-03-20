package object.strategy;

import object.Coordinate;
import object.Route;
import object.piece.Pieces;
import object.piece.Team;

public class GuardStrategy implements MoveStrategy {

    private static final String NOT_IMPLEMENTED_FEATURE = "아직 구현되지 않은 기능입니다.";
    
    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        throw new IllegalStateException(NOT_IMPLEMENTED_FEATURE);
    }

    @Override
    public Coordinate move(Coordinate destination, Pieces onRoutePieces, Team moveTeam) {
        throw new IllegalStateException(NOT_IMPLEMENTED_FEATURE);
    }
}
