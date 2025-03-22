package janggi.piece;

import janggi.board.Position;
import janggi.move.Route;
import java.util.List;
import java.util.Map;

public class Empty implements Piece {

    public Empty() {
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        throw new IllegalArgumentException("[ERROR] 이 위치에는 말이 존재하지 않습니다.");
    }

    @Override
    public List<Position> filterReachableDestinations(List<Route> routes, Map<Position, Piece> board) {
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
