package domain.movement;

import domain.board.Position;

public interface Movement {
    Paths candidatePaths(Position from);
}
