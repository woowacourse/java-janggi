package model.policy;

import model.board.Board;
import model.position.Position;

public abstract class PathPolicy {
    public abstract boolean check(Position pos, Board board);
}
