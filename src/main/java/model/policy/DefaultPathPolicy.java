package model.policy;

import model.board.Board;
import model.position.Position;

public class DefaultPathPolicy extends PathPolicy {
    @Override
    public boolean check(Position pos, Board board) {
        return board.isPathEmpty(pos);
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
