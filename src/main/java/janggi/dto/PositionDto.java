package janggi.dto;

import janggi.domain.Position;

public record PositionDto(int x, int y) {
    public static PositionDto from(Position position) {
        return new PositionDto(position.getX(), position.getY());
    }
}
