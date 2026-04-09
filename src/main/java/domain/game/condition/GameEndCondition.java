package domain.game.condition;

import domain.board.Board;
import domain.game.GameRecord;

public interface GameEndCondition {
    boolean isSatisfied(Board board, GameRecord record);
}
