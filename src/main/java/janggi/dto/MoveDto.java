package janggi.dto;

import janggi.domain.position.Position;

public record MoveDto(
        Position startPosition,
        Position endPosition
) {
}
