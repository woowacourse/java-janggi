package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.MoveRule;
import java.util.Collection;
import java.util.List;

public class PieceAction {
    private final List<MoveRule> movementStrategies;

    public PieceAction(List<MoveRule> movementStrategies) {
        this.movementStrategies = List.copyOf(movementStrategies);
    }

    public List<Position> calculateMovablePositions(final Position from, final BoardMediator boardMediator) {
        return movementStrategies.stream()
                .map(rule -> rule.execute(from, boardMediator))
                .flatMap(Collection::stream)
                .toList();
    }
}
