package dto;

import domain.coordinate.Position;

import java.util.List;

public class PossibleMovesDto {

    private final List<Position> possibleMoves;
    private final int possibleMoveCount;

    public PossibleMovesDto(List<Position> possibleMoves, int possibleMoveCount) {
        this.possibleMoves = possibleMoves;
        this.possibleMoveCount = possibleMoveCount;
    }

    public List<Position> getPossibleMoves() {
        return possibleMoves;
    }

    public int getPossibleMoveCount() {
        return possibleMoveCount;
    }
}
