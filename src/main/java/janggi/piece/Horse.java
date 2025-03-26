package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Horse implements Piece {

    private static final List<List<Movement>> movements = List.of(
            List.of(Movement.UP, Movement.RIGHT_UP),
            List.of(Movement.UP, Movement.LEFT_UP),
            List.of(Movement.LEFT, Movement.LEFT_UP),
            List.of(Movement.LEFT, Movement.LEFT_DOWN),
            List.of(Movement.RIGHT, Movement.RIGHT_UP),
            List.of(Movement.RIGHT, Movement.RIGHT_DOWN),
            List.of(Movement.DOWN, Movement.RIGHT_DOWN),
            List.of(Movement.DOWN, Movement.LEFT_DOWN)
    );

    private final Team team;
    private Position position;

    public Horse(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public void move(Position arrivedPosition, List<Piece> positioningPiece) {
        List<Movement> availableMovement = findAvailableMovementByArrivedPosition(arrivedPosition);
        checkAlreadyOccupiedPosition(arrivedPosition, positioningPiece);
        List<Position> pathPositions = extractPathPositions(availableMovement, arrivedPosition);
        checkObstacleOfPath(pathPositions, positioningPiece);
        position = step(availableMovement);
    }

    private List<Movement> findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        return movements.stream()
                .filter(movements -> !arrivedPosition.isOutOfBoards() && step(movements).equals(arrivedPosition))
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

    private List<Position> extractPathPositions(List<Movement> availableMovements, Position arrivedPosition) {
        List<Position> pathPositions = new ArrayList<>();
        for (int i = 0; i < availableMovements.size(); i++) {
            Position pathPosition = position;
            for (int j = 0; j <= i; j++) {
                Movement movement = availableMovements.get(j);
                pathPosition = movement.move(pathPosition);
            }
            pathPositions.add(pathPosition);
        }
        return pathPositions.stream()
                .filter(position -> !position.equals(arrivedPosition))
                .toList();
    }

    private void checkObstacleOfPath(List<Position> pathPositions, List<Piece> positioningPiece) {
        boolean isObstacle = positioningPiece.stream()
                .anyMatch(piece -> piece.isObstacle(pathPositions));
        if (isObstacle) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다");
        }
    }

    private Position step(List<Movement> movements) {
        Position arrivedPosition = position;
        for (Movement movement : movements) {
            arrivedPosition = movement.move(arrivedPosition);
        }
        return arrivedPosition;
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
        return PieceType.HORSE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return team == horse.team && Objects.equals(position, horse.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
