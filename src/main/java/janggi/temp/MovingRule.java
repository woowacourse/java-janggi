package janggi.temp;

import java.util.List;

public final class MovingRule {

    private final List<Movement> movingRule;

    public MovingRule(final Movement movement) {
        this.movingRule = List.of(movement);
    }
}
