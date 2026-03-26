package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.Optional;

public interface Piece {

    boolean isValidMovePattern(int startX, int startY, int endX, int endY);

    Optional<MovePath> findMovePath(int startX, int startY, int endX, int endY);

    default boolean isValidPath(Position start, Position end, Board board) {
        return findMovePath(start.getX(), start.getY(), end.getX(), end.getY()).isPresent();
    }

    String nickname();

    boolean isSameType(TeamType nowTurn);

    PieceType getPieceType();
}
