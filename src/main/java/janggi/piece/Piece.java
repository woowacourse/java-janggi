package janggi.piece;

import janggi.movement.Movement;
import janggi.movement.Route;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

public interface Piece {
    Piece move(Position arrivedPosition);

    Route findAvailableMovementByArrivedPosition(Position arrivedPosition);

    List<Position> extractPathPositions(Route availableMovements, Position arrivedPosition);

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
