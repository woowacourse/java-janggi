package domain.game.condition;

import domain.board.Board;
import domain.game.GameRecord;

public class ConsecutivePassCondition implements GameEndCondition {
    private static final int DRAW_PASS_COUNT = 2;

    @Override
    public boolean isSatisfied(Board board, GameRecord record) {
        return record.consecutivePassCount() >= DRAW_PASS_COUNT;
    }
}
