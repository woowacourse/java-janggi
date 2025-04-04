package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

public interface Piece {
    Position move(Position startPosition, Position arrivedPosition);

    Movement findAvailableMovementByArrivedPosition(Position startPosition, Position arrivedPosition);

    List<Position> extractPathPositions(Position startPosition, Position arrivedPosition);

    boolean isSameTeam(Team team);

    boolean isJumpable();

    Team getTeam();

    PieceType getPieceType();

}
