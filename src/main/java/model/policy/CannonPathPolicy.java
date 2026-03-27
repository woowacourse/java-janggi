package model.policy;

import model.board.Board;
import model.position.Position;

public class CannonPathPolicy implements PathPolicy {
    private int count = 0;

    @Override
    public boolean check(Position pos, Board board) {
        if (!board.isPathEmpty(pos)) {
            count++;
        }

        return count <= 1;
    }
}
