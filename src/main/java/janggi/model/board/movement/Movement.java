package janggi.model.board.movement;

import janggi.model.board.moveResult.MoveResult;
import janggi.model.board.position.Position;

public interface Movement {
    MoveResult move(Position from, Position to);
}
