package application;

import model.coordinate.Position;

public record MoveCommand(Position current, Position next) {
}
