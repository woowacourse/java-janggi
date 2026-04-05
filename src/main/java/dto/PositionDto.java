package dto;

import domain.board.Position;

public record PositionDto(int column, int row) {

    public static PositionDto of(final Position position) {
        return new PositionDto(position.column(), position.row());
    }
}
