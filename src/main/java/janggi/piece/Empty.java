package janggi.piece;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class Empty extends Piece {

    public Empty() {
        super(Symbol.EMPTY, Side.NONE);
    }

    @Override
    public List<Position> filterReachableDestinations(final Position selectedPosition, final JanggiBoard board) {
        throw new IllegalArgumentException("[ERROR] 이 위치에는 말이 존재하지 않습니다.");
    }

}
