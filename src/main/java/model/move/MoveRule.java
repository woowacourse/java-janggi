package model.move;

import java.util.List;
import model.board.Board;
import model.board.Country;

public abstract class MoveRule {
    public boolean matches(Move move, Board board, Country country) {
        return patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board, country));
    }

    protected abstract List<MovePattern> patterns(Move move);
}
