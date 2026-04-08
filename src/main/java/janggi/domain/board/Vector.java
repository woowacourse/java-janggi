package janggi.domain.board;

import janggi.domain.rule.route.Direction;

public record Vector(
        Direction direction,
        int distance
) {
}
