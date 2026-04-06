package janggi.dto;

import janggi.domain.common.Position;

public record PositionResponse(int x, int y) {
    public static PositionResponse from(Position position) {
        return new PositionResponse(position.getX(), position.getY());
    }
}
