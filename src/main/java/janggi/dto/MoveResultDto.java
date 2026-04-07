package janggi.dto;

import janggi.domain.Position;

public record MoveResultDto(
        Position source,
        Position destination,
        boolean captured
) {
}
