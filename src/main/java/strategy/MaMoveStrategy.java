package strategy;

import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public class MaMoveStrategy implements MoveStrategy {
    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition) {
        return null;
    }

    @Override
    public Position move(Position destination, Pieces onRoutePieces, Team moveTeam) {
        return null;
    }
}
