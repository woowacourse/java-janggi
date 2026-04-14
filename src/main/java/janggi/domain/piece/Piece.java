package janggi.domain.piece;

import janggi.domain.movepath.MovePathStrategy;
import janggi.domain.team.TeamType;
import janggi.dto.MoveRoute;
import java.util.Optional;

public interface Piece {

    boolean isValidMovePattern(int startX, int startY, int endX, int endY);

    Optional<MovePathStrategy> findMovePath(int startX, int startY, int endX, int endY);

    boolean canMove(MoveRoute moveRoute);

    String nickname();

    PieceType getPieceType();

    TeamType getTeamType();

    int getScore();
}
