package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Objects;

public class Guard implements Piece {

    private static final List<Movement> movements = List.of(
            Movement.UP,
            Movement.DOWN,
            Movement.RIGHT,
            Movement.LEFT,
            Movement.RIGHT_UP,
            Movement.RIGHT_DOWN,
            Movement.LEFT_UP,
            Movement.LEFT_DOWN
    );

    private final Team team;
    private Position position;

    public Guard(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public void move(Position arrivedPosition, List<Piece> positioningPiece) {
        Movement availableMovement = findAvailableMovementByArrivedPosition(arrivedPosition);
        checkAlreadyOccupiedPosition(arrivedPosition, positioningPiece);
        position = step(availableMovement);
    }

    private Movement findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        return movements.stream()
                .filter(movement -> !arrivedPosition.isOutOfBoards() && step(movement).equals(arrivedPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    private void checkAlreadyOccupiedPosition(Position arrivedPosition, List<Piece> positioningPiece) {
        for (Piece piece : positioningPiece) {
            if (piece.matchesPosition(arrivedPosition) && piece.isSameTeam(this.team)) {
                throw new IllegalArgumentException("도착 위치에 아군 기물이 존재합니다");
            }
        }
    }

    private Position step(Movement movement) {
        return movement.move(position);
    }

    @Override
    public boolean isObstacle(List<Position> pathPositions) {
        return pathPositions.stream()
                .anyMatch(pathPosition -> pathPosition.equals(position));
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    @Override
    public boolean matchesPosition(Position position) {
        return this.position.equals(position);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public PieceType getpieceType() {
        return PieceType.GUARD;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Guard guard = (Guard) o;
        return team == guard.team && Objects.equals(position, guard.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
