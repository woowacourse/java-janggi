package domain.game.condition;

import domain.board.Board;
import domain.game.progress.GameProgress;

public interface GameEndCondition {
    boolean isSatisfied(Board board, GameProgress progress);
}
