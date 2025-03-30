package dto;

import domain.board.BoardPoint;

public record MovementRequestDto(
        BoardPoint startPoint,
        BoardPoint arrivalPoint
) {
}
