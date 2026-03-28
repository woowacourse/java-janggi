package view;

import domain.board.Position;

public record MoveCommand(Position from, Position to) implements GameCommand {
}
