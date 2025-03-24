package janggi.piece;

import janggi.board.Position;
import janggi.move.Route;
import java.util.List;
import java.util.Map;

public interface Piece {

    PieceType getType();

    List<Position> computeReachableDestinations(final Position position, final Map<Position, Piece> board);

    boolean isHan();

    boolean isCho();

    default boolean isAlly(Piece piece) {
        if (isCho()) {
            return piece.isCho();
        }
        if (isHan()) {
            return piece.isHan();
        }
//        throw new IllegalStateException("[ERROR] 프로그램에 오류가 생겼습니다."); //Empty일 경우 예외를 던짐. 그냥 false를 반환한다면?
        return false;
    }

    default boolean isOccupied() {
        return !(getType() == PieceType.EMPTY);
    }

    default boolean isNotJumpable() {
        return getType() == PieceType.CANNON;
    }

    default boolean isGameOver() {
        return getType() == PieceType.KING;
    }
}
