package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Cannon implements Piece {

    private static final List<List<Movement>> movements = List.of(
            Collections.nCopies(10, Movement.UP),
            Collections.nCopies(10, Movement.DOWN),
            Collections.nCopies(10, Movement.RIGHT),
            Collections.nCopies(10, Movement.LEFT)
    );

    private final Team team;
    private Position position;

    public Cannon(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public void move(Position arrivedPosition, List<Piece> positioningPiece) {
        List<Movement> availableMovement = findAvailableMovementByArrivedPosition(arrivedPosition);
        checkAlreadyOccupiedPosition(arrivedPosition, positioningPiece);
        List<Position> pathPositions = extractPathPositions(availableMovement, arrivedPosition);
        checkObstacleOfPath(pathPositions, positioningPiece);
        position = step(availableMovement, arrivedPosition);
    }

    private List<Movement> findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        return movements.stream()
                .filter(movement -> !arrivedPosition.isOutOfBoards() && step(movement, arrivedPosition).equals(arrivedPosition))
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
        int arrivedValue = 0;
        if (position.isHorizontalFromPosition(arrivedPosition)) {
            arrivedValue = position.calculateColumnDistance(arrivedPosition);
        }
        if (position.isVerticalFromPosition(arrivedPosition)) {
            arrivedValue = position.calculateRowDistance(arrivedPosition);
        }
        for (int i = 0; i < arrivedValue; i++) {
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
        List<Piece> obstacles = positioningPiece.stream()
                .filter(piece -> piece.isObstacle(pathPositions))
                .toList();
        if (obstacles.size() != 1) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다");
        }
        Piece obstacle = obstacles.getFirst();
        if (obstacle.canNotJumpOver()) {
            throw new IllegalArgumentException("넘을 수 없는 기물입니다");
        }
    }

    private Position step(List<Movement> movements, Position arrivedPosition) {
        Position reachablePosition = position;
        if (position.isHorizontalFromPosition(arrivedPosition)) {
            for (Movement movement : movements) {
                reachablePosition = movement.move(reachablePosition);
                if (reachablePosition.isSameColumn(arrivedPosition)) {
                    return reachablePosition;
                }
            }
        }

        if (position.isVerticalFromPosition(arrivedPosition)) {
            for (Movement movement : movements) {
                reachablePosition = movement.move(reachablePosition);
                if (reachablePosition.isSameRow(arrivedPosition)) {
                    return reachablePosition;
                }
            }
        }

        for (Movement movement : movements) {
            reachablePosition = movement.move(reachablePosition);
        }
        return reachablePosition;
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
    public boolean isObstacle(List<Position> pathPositions) {
        return pathPositions.stream()
                .anyMatch(pathPosition -> pathPosition.equals(position));
    }

    @Override
    public boolean canNotJumpOver() {
        return true;
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
        return PieceType.CANNON;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cannon cannon = (Cannon) o;
        return team == cannon.team && Objects.equals(position, cannon.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
