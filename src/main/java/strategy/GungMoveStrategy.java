package strategy;

import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public class GungMoveStrategy implements MoveStrategy {

    @Override
    public Route getLegalRoute(piece.Position startPosition, piece.Position endPosition) {
        return null;
    }

    @Override
    public piece.Position move(Position destination, Pieces onRoutePieces, Team moveTeam) {
        return null;
    }
}
