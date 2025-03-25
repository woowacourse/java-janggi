package janggi.domain.moveRule;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.moveRule.moveStrategy.MoveStrategy;
import janggi.domain.moveRule.routeValidator.RouteValidator;
import janggi.domain.piece.Piece;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class MoveRule {
    private final MoveStrategy moveStrategy;
    private final RouteValidator routeValidator;

    public MoveRule(MoveStrategy moveStrategy, RouteValidator routeValidator) {
        this.moveStrategy = moveStrategy;
        this.routeValidator = routeValidator;
    }

    public boolean verifyMovement(PiecePath path, TeamColor teamColor) {
        return moveStrategy.isValidMovement(path, teamColor);
    }

    public List<Position> findAllRoute(PiecePath path) {
        return moveStrategy.findAllRoute(path);
    }

    public boolean verifyRoute(Piece piece, Piece destinationPiece, List<Piece> allPiecesOnRoute) {
        return routeValidator.canMoveAlongRoute(piece, destinationPiece, allPiecesOnRoute);
    }
}
