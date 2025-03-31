package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

public interface Piece {
    Piece move(Position arrivedPosition);

    List<Movement> findAvailableMovementByArrivedPosition(Position arrivedPosition);

    List<Position> extractPathPositions(List<Movement> availableMovements, Position arrivedPosition);

    boolean isSameTeam(Team team);

    boolean matchesPosition(Position position);

    boolean isObstacle(List<Position> pathPositions);

    boolean canNotJumpingOver();

    Position getPosition();

    Team getTeam();

    PieceType getPieceType();

    Piece receiveAttack();

    boolean isLive();
}
