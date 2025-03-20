package janggi.piece.strategy.block;

import janggi.Board;
import janggi.Position;

public interface BlockStrategy {

    String exceptionMessage = "이동 경로에 기물 갯수가 조건에 맞지 않습니다.";

    void validate(final Board board, final Position departure, final Position destination);
}
