package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.Optional;

public interface Piece {

    boolean isValidMovePattern(Position startPosition, Position endPosition);

    Optional<MovePath> findMovePath(Position startPosition, Position endPosition);

    String name();

    PieceType getPieceType();

    TeamType getTeamType();

    void validateCanMove(Position start, Position end, Board board);
}
