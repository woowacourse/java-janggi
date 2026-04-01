package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.team.TeamType;
import java.util.Optional;

public interface Piece {

    boolean isValidMovePattern(int startX, int startY, int endX, int endY);

    Optional<MovePath> findMovePath(int startX, int startY, int endX, int endY);

    boolean isObstaclesNotExist(Position start, Position end, Board board);

    String nickname();

    PieceType getPieceType();

    TeamType getTeamType();
}
