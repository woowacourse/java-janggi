package janggi.domain.moveRule;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.moveRule.pathStrategy.PathStrategy;
import janggi.domain.moveRule.routeStrategy.RouteStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class MoveRule {
    private final PathStrategy pathStrategy;
    private final RouteStrategy RouteStrategy;

    public MoveRule(PathStrategy pathStrategy, RouteStrategy RouteStrategy) {
        this.pathStrategy = pathStrategy;
        this.RouteStrategy = RouteStrategy;
    }

    public boolean verifyMovement(PiecePath path, TeamColor teamColor) {
        return pathStrategy.isValidMovement(path, teamColor);
    }

    public List<Position> findAllRoute(PiecePath path) {
        return pathStrategy.findAllIntermediatePositions(path);
    }

    public boolean verifyRoute(Piece piece, Piece destinationPiece, List<Piece> allPiecesOnRoute) {
        return RouteStrategy.canMoveAlongRoute(piece, destinationPiece, allPiecesOnRoute);
    }
}
