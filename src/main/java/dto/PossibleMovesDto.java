package dto;

import domain.coordinate.Position;

import java.util.List;

public record PossibleMovesDto(List<Position> possibleMoves, int possibleMoveCount) {
}
