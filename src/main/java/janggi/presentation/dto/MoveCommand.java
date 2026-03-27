package janggi.presentation.dto;

import janggi.domain.Point;

public record MoveCommand(
        Point from,
        Point to
) {
}
