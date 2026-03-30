package janggi.model.board.movement;

import janggi.model.board.PositionPath;
import janggi.model.board.position.Position;

public interface Movement {
    PositionPath move(Position from, Position to);
}
