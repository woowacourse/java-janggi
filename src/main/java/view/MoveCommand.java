package view;

import domain.board.Point;

public record MoveCommand(Point source, Point destination) {
}
