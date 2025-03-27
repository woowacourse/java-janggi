package janggi.temp;

import java.util.Arrays;
import java.util.List;

public final class MovingRules {

    private final List<MovingRule> movingRules;

    public MovingRules(final Movement... movements) {
        this.movingRules = Arrays.stream(movements)
                .map(MovingRule::new)
                .toList();
    }
}
