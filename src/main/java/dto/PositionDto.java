package dto;

import janggi.board.Position;

public record PositionDto(
    int x,
    int y
) {
    public static PositionDto from(Position position) {
        return new PositionDto(position.x(), position.y());
    }
}
