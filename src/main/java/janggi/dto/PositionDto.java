package janggi.dto;

import janggi.domain.board.Position;

public record PositionDto(int row, int col) {

    public static PositionDto from(Position position) {
        return new PositionDto(position.row(), position.column());
    }
}
