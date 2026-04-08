package persistence;

import domain.Position;

public record MoveCommand(
        Position source,
        Position target
) {
}
