package dto;

import domain.board.BoardPoint;

public record MovementResponseDto(
        BoardPoint startBoardPoint,
        BoardPoint arrivalBoardPoint
) {
}
