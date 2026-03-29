package domain.movement;

import domain.game.Position;

public interface Movement {
    Paths candidatePaths(Position from);
}