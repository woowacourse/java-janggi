package janggi.model.movement;

import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;

public interface Movement {
    PositionPath move(Position from, Position to);
}
