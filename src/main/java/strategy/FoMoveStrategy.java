package strategy;

import piece.Pieces;
import piece.Route;
import piece.Team;

public class FoMoveStrategy implements MoveStrategy {

    @Override
    public Route getLegalRoute(piece.Position startPosition, piece.Position endPosition) {
        return null;
    }

    @Override
    public piece.Position move(Pieces onRoutePieces, piece.Position destination, Team moveTeam) {
        return null;
    }
}
