package view;

import domain.point.Point;

public record MoveCommand(Point source, Point destination) {
}
