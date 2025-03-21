package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public abstract class Piece {

    protected static final int MOVE_LIMIT = 10;

    private final Side side;

    protected Piece(final Side side) {
        this.side = side;
    }

    public abstract List<Route> computeCandidatePositions(final Position position);

    public abstract String getSymbol();

    public boolean isAllyWith(final Piece anotherPiece) {
        if (isCho()) {
            return anotherPiece.isCho();
        }
        if (isHan()) {
            return anotherPiece.isHan();
        }
        throw new IllegalStateException("[ERROR] 프로그램에 오류가 발생했습니다.");
    }

    public boolean isEnemyWith(final Piece anotherPiece) {
        return !isAllyWith(anotherPiece);
    }

    public boolean isCho() {
        return side == Side.CHO;
    }

    public boolean isHan() {
        return side == Side.HAN;
    }

}
