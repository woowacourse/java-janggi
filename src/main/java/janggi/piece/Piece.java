package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

public interface Piece {
    void move(Position arrivedPosition);

    List<Movement> findAvailableMovementByArrivedPosition(Position arrivedPosition);

    List<Position> extractPathPositions(List<Movement> availableMovements, Position arrivedPosition);

    boolean isSameTeam(Team team);

    boolean matchesPosition(Position position);

    boolean isObstacle(List<Position> pathPositions);

    boolean canNotJumpingOver();

    Position getPosition();

    Team getTeam();

    PieceType getpieceType();

    void receiveAttack();

    boolean isLive();
}
