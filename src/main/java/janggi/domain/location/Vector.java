package janggi.domain.location;

import janggi.domain.rule.route.Direction;

public record Vector(
        Direction direction,
        int distance
) {
}
