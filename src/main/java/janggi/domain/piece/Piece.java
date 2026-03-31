package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.Optional;

public interface Piece {

    boolean isValidMovePattern(Position start, Position end);

    Optional<MovePath> findMovePath(Position start, Position end);

    String name();

    PieceType getPieceType();

    TeamType getTeamType();

    void validateCanMove(Position start, Position end, Board board);
}
