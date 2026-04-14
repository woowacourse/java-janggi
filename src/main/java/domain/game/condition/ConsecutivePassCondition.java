package domain.game.condition;

import domain.board.Board;
import domain.game.progress.GameProgress;

public class ConsecutivePassCondition implements GameEndCondition {
    private static final int DRAW_PASS_COUNT = 2;

    @Override
    public boolean isSatisfied(Board board, GameProgress progress) {
        return progress.consecutivePassCount() >= DRAW_PASS_COUNT;
    }
}
