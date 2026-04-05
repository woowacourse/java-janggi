package repository.command;

import model.coordinate.Position;
import model.game.Team;

public record MoveCommand(Position source, Position destination, Team turn) {
}
