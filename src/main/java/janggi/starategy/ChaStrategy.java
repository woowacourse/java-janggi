package janggi.starategy;

import janggi.direction.BeelineDirection;
import janggi.piece.Piece;
import janggi.value.Position;
import java.util.List;
import java.util.function.BiFunction;

public class ChaStrategy implements MoveStrategy {

    @Override
    public boolean ableToMove(Position start, Position destination, List<Piece> enemy, List<Piece> allies) {
        BeelineDirection direction = BeelineDirection.parse(start, destination);
        List<Position> positionsInPath = calculatePositionsInPath(start, direction, destination);

        boolean followRuleOfMove = checkRuleOfMove(direction);
        boolean existHurdleInPath = existHurdleInPath(positionsInPath, enemy, allies);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return followRuleOfMove && !existHurdleInPath && !existAlliesInDestination;
    }

    private List<Position> calculatePositionsInPath(Position start, BeelineDirection direction, Position destination) {
        BiFunction<Position, Position, List<Position>> calculationMethod = direction.getCalculatePositionsInPath();
        return calculationMethod.apply(start, destination);
    }

    private boolean checkRuleOfMove(BeelineDirection direction) {
        return direction != BeelineDirection.NONE;
    }

    private boolean existHurdleInPath(List<Position> positionsInPath, List<Piece> enemy, List<Piece> allies) {
        boolean existEnemyInPath = existPieceInPath(positionsInPath, enemy);
        boolean existAlliesInPath = existPieceInPath(positionsInPath, allies);
        return existEnemyInPath || existAlliesInPath;
    }

    private boolean existPieceInPath(List<Position> positions, List<Piece> pieces) {
        return positions.stream()
                .anyMatch(position -> existPieceInPosition(position, pieces));
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> position.equals(piece.getPosition()));
    }
}
