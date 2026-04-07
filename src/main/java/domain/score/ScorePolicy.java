package domain.score;

import domain.Side;
import domain.board.Board;

public interface ScorePolicy {
    int calculate(Board board, Side side);
}
