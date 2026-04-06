package janggi.dto;

import janggi.domain.board.Position;

public record PositionDTO(int row, int col) {

    public static PositionDTO from(Position position) {
        return new PositionDTO(position.row(), position.column());
    }
}
