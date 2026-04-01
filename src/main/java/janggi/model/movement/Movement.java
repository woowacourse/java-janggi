package janggi.model.movement;

import janggi.model.position.PositionPath;
import janggi.model.position.Position;

public interface Movement {
    PositionPath move(Position from, Position to);
}
