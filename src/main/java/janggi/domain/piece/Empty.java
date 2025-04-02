package janggi.domain.piece;

import janggi.domain.Turn;
import janggi.domain.board.Position;

import java.util.List;
import java.util.Map;

public class Empty implements Piece {

    public Empty() {
    }

    public Empty(Turn side) {
    }

    @Override
    public List<Position> computeReachableDestinations(final Position position, final Map<Position, Piece> board) {
        throw new IllegalArgumentException("[ERROR] 프로그램에 오류가 발생했습니다.");
    }

    @Override
    public PieceType getType() {
        return PieceType.EMPTY;
    }

    @Override
    public boolean isCho() {
        return false;
    }

    @Override
    public boolean isHan() {
        return false;
    }

    @Override
    public Turn getTurn() {
        return Turn.NONE;
    }
}
