package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

public interface Piece {
    void move(Position arrivedPosition, List<Piece> positioningPiece);

    boolean isSameTeam(Team team);

    boolean matchesPosition(Position position);

    boolean isObstacle(List<Position> pathPositions);

    boolean canNotJumpOver();

    Position getPosition();

    Team getTeam();

    PieceType getpieceType();
}
