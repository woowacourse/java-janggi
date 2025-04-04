package janggi.movement;

import janggi.position.Position;

import java.util.List;

public interface Movement {
    Position step(Position startPosition, Position arrivedPosition);
    List<Position> extractPathPositions(Position startPosition, Position arrivedPosition);
}
