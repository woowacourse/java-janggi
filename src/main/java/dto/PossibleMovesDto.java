package dto;

import java.util.List;

public record PossibleMovesDto(List<PositionDto> possibleMoves, int possibleMoveCount) {
}
