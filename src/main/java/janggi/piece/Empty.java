package janggi.piece;

import janggi.board.Position;
import janggi.move.Route;

import java.util.List;

public class Empty implements Piece {

    public Empty() {
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        throw new IllegalArgumentException("[ERROR] 이 위치에는 말이 존재하지 않습니다.");
    }

    @Override
    public String getSymbol() {
        return "·";
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
