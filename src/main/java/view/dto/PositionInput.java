package view.dto;

import domain.position.Position;

public record PositionInput(boolean quit, Position position) {

    public static PositionInput quitting() {
        return new PositionInput(true, null);
    }

    public static PositionInput of(Position position) {
        return new PositionInput(false, position);
    }
}
