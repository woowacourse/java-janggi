package model.policy;

import model.board.Board;
import model.position.Position;

public interface PathPolicy {
    boolean check(Position pos, Board board);
}
