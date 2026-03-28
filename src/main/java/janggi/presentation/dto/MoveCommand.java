package janggi.presentation.dto;

import janggi.domain.point.Point;

public record MoveCommand(
        Point from,
        Point to
) {
}
