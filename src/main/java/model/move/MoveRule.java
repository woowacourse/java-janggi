package model.move;

import java.util.List;
import model.board.Board;

public abstract class MoveRule {
    private final List<MovePattern> movePatterns;

    protected MoveRule(List<MovePattern> movePatterns) {
        this.movePatterns = movePatterns;
    }

    public boolean matches(Move move, Board board) {
        for (MovePattern pattern : movePatterns) {
            if (pattern.matches(move, board)) {
                return true;
            }
        }
        return false;
    }

    public List<MovePattern> movePatterns() {
        return movePatterns;
    }
}
