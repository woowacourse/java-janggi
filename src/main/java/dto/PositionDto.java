package dto;

import domain.piece.Position;

public record PositionDto(int column, int row) {

    public static PositionDto from(Position position) {
        return new PositionDto(position.column(), position.row());
    }
}
