package domain.score;

import domain.Side;
import domain.board.Board;

public interface ScorePolicy {
    double calculate(Board board, Side side);
}
