package view.command;

import domain.board.Point;

public record MoveCommand(Point source, Point destination) {
}
