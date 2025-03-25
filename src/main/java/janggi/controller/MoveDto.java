package janggi.controller;

import janggi.domain.position.Position;

public record MoveDto(
        Position startPosition,
        Position endPosition
) {
}
