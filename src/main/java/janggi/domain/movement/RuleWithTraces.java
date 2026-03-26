package janggi.domain.movement;

import janggi.domain.Position;
import java.util.ArrayList;
import java.util.List;

public class RuleWithTraces implements Rule {

    private final List<Movement> movementOrder;

    public RuleWithTraces(final List<Movement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    @Override
    public List<Position> execute(Position from) {
        final List<Position> traces = new ArrayList<>();
        final List<Movement> movementOrderWithoutLast = getMovementOrderWithoutLast();
        final Movement lastMovement = movementOrder.getLast();
        for (final Movement movement: movementOrderWithoutLast) {
            traces.addAll(movement.calculateTraces(from));
            traces.add(movement.calculateDestination(from));
            from = traces.getLast();
        }
        traces.addAll(lastMovement.calculateTraces(from));
        return traces;
    }

    private List<Movement> getMovementOrderWithoutLast() {
        final List<Movement> movementOrderWithoutLast = new ArrayList<>(movementOrder);
        movementOrderWithoutLast.removeLast();
        return movementOrderWithoutLast;
    }
}
