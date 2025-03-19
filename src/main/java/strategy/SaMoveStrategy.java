package strategy;

import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public class SaMoveStrategy implements MoveStrategy {

    private static final String NOT_IMPLEMENTED_FEATURE = "아직 구현되지 않은 기능입니다.";
    
    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition) {
        throw new IllegalStateException(NOT_IMPLEMENTED_FEATURE);
    }

    @Override
    public Position move(Position destination, Pieces onRoutePieces, Team moveTeam) {
        throw new IllegalStateException(NOT_IMPLEMENTED_FEATURE);
    }
}
