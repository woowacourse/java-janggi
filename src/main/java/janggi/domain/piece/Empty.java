package janggi.domain.piece;

import janggi.domain.board.Position;

import java.util.List;
import java.util.Map;

public class Empty implements Piece {

    public Empty() {
    }

    public Empty(Side side) { //FIXME: 피스 타입에서 생성자를 위한 Side를 받는 생성자입니다.
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
}
