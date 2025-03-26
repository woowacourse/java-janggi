package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Chariot implements Piece {

    private static final int POSSIBLE_MOVEMENT_COUNT = 10;
    private static final List<List<Movement>> movements = List.of(
            Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.UP),
            Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.DOWN),
            Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.RIGHT),
            Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.LEFT)
    );

    private final Team team;
    private Position position;

    public Chariot(Team team, Position position) {
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
            arrivedValue = Math.abs(position.calculateColumnDistance(arrivedPosition));
        }
        if (position.isVerticalFromPosition(arrivedPosition)) {
            arrivedValue = Math.abs(position.calculateRowDistance(arrivedPosition));
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
        boolean isObstacle = positioningPiece.stream()
                .anyMatch(piece -> piece.isObstacle(pathPositions));
        if (isObstacle) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다");
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
        return false;
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
        return PieceType.CHARIOT;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Chariot chariot = (Chariot) o;
        return team == chariot.team && Objects.equals(position, chariot.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
