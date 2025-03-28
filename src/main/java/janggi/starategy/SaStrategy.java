package janggi.starategy;

import janggi.direction.OneStepDirection;
import janggi.piece.Piece;
import janggi.value.Position;
import java.util.List;

public class SaStrategy implements MoveStrategy {

    @Override
    public boolean ableToMove(Position start, Position destination, List<Piece> enemy, List<Piece> allies) {
        OneStepDirection direction = OneStepDirection.parse(start, destination);

        boolean followRuleOfMove = checkRuleOfMove(direction);
        boolean existAlliesInDestination = existPieceInDestination(destination, allies);
        return followRuleOfMove && !existAlliesInDestination;
    }

    private boolean checkRuleOfMove(OneStepDirection direction) {
        return direction != OneStepDirection.NONE;
    }

    private boolean existPieceInDestination(Position destination, List<Piece> allies) {
        return allies.stream()
                .anyMatch(piece -> piece.getPosition().equals(destination));
    }
}
