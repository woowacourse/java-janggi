package janggi.domain.moveRule;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.moveRule.pathStrategy.PathStrategy;
import janggi.domain.moveRule.moveStrategy.moveStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class MoveRule {
    private final PathStrategy pathStrategy;
    private final moveStrategy moveStrategy;

    public MoveRule(PathStrategy pathStrategy, moveStrategy moveStrategy) {
        this.pathStrategy = pathStrategy;
        this.moveStrategy = moveStrategy;
    }

    public boolean verifyMovement(PiecePath path, TeamColor teamColor) {
        return pathStrategy.isValidMovement(path, teamColor);
    }

    public List<Position> findAllRoute(PiecePath path) {
        return pathStrategy.findAllRoute(path);
    }

    public boolean verifyRoute(Piece piece, Piece destinationPiece, List<Piece> allPiecesOnRoute) {
        return moveStrategy.canMoveAlongRoute(piece, destinationPiece, allPiecesOnRoute);
    }
}
