package strategy;

import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public class SangMoveStrategy implements MoveStrategy {
    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition) {
        return null;
    }

    @Override
    public Position move(Pieces onRoutePieces, Position destination, Team moveTeam) {
        return null;
    }
}
