package model.policy;

import model.board.Board;
import model.position.Position;

public class DefaultPathPolicy implements PathPolicy {
    @Override
    public boolean check(Position pos, Board board) {
        return board.isPathEmpty(pos);
    }
}
