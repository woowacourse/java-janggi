package janggi.starategy;

import janggi.direction.OneStepDirection;
import janggi.piece.Piece;
import janggi.value.Position;
import java.util.List;

public class JolStrategy implements MoveStrategy {

    @Override
    public boolean ableToMove(Position start, Position destination, List<Piece> enemy, List<Piece> allies) {
        OneStepDirection direction = OneStepDirection.parse(start, destination);

        boolean followRuleOfMove = checkRuleOfMove(direction);
        boolean existAlliesInDestination = existPieceInDestination(destination, allies);
        return followRuleOfMove && !existAlliesInDestination;
    }

    private boolean checkRuleOfMove(OneStepDirection direction) {
        if (direction == OneStepDirection.NONE) {
            return false;
        }
        return direction != OneStepDirection.DOWN;
    }

    private boolean existPieceInDestination(Position destination, List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(destination));
    }
}
