package model.move;

import java.util.List;
import model.board.Board;

public abstract class MoveRule {
    public boolean matches(Move move, Board board) {
        for (MovePattern pattern : patterns(move)) {
            if (pattern.matches(move, board)) {
                return true;
            }
        }
        return false;
    }

    protected abstract List<MovePattern> patterns(Move move);
}
