package model.move;

import java.util.List;
import model.board.Board;

public abstract class MoveRule {
    public boolean matches(Move move, Board board) {
        return patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board));
    }

    protected abstract List<MovePattern> patterns(Move move);
}
