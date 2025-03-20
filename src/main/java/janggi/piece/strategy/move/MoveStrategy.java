package janggi.piece.strategy.move;

import janggi.Board;
import janggi.Position;

public interface MoveStrategy {

    String exceptionMessage = "이동할 수 없는 지점입니다.";

    void validate(final Board board, final Position departure, final Position destination);
}
