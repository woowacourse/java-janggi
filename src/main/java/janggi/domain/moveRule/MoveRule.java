package janggi.domain.moveRule;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.moveRule.movementStrategy.MovementStrategy;
import janggi.domain.moveRule.routeStrategy.RouteStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class MoveRule {
    private final MovementStrategy movementStrategy;
    private final RouteStrategy RouteStrategy;

    public MoveRule(MovementStrategy movementStrategy, RouteStrategy RouteStrategy) {
        this.movementStrategy = movementStrategy;
        this.RouteStrategy = RouteStrategy;
    }

    public boolean verifyMovement(PiecePath path, TeamColor teamColor) {
        return movementStrategy.isValidMovement(path, teamColor);
    }

    public List<Position> findAllRoute(PiecePath path) {
        return movementStrategy.findAllIntermediatePositions(path);
    }

    public boolean verifyRoute(Piece piece, Piece destinationPiece, List<Piece> allPiecesOnRoute) {
        return RouteStrategy.canMoveAlongRoute(piece, destinationPiece, allPiecesOnRoute);
    }
}
