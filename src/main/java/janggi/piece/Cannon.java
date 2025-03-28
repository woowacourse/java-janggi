package janggi.piece;

import janggi.position.PalacePosition;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Cannon implements Piece {
    private static final List<List<Movement>> MOVEMENTS = List.of(
            Collections.nCopies(10, Movement.UP),
            Collections.nCopies(10, Movement.DOWN),
            Collections.nCopies(10, Movement.RIGHT),
            Collections.nCopies(10, Movement.LEFT)
    );

    private static final List<List<Movement>> PALACE_MOVEMENTS = List.of(
            Collections.nCopies(2, Movement.RIGHT_UP),
            Collections.nCopies(2, Movement.RIGHT_DOWN),
            Collections.nCopies(2, Movement.LEFT_UP),
            Collections.nCopies(2, Movement.LEFT_DOWN)
    );

    private final Team team;
    private Position position;
    private boolean isLive;
    private PieceType pieceType;

    public Cannon(Team team, Position position) {
        this.team = team;
        this.position = position;
        this.isLive = true;
        this.pieceType = PieceType.CANNON;
    }

    @Override
    public void move(Position arrivedPosition) {
        List<Movement> availableMovement = findAvailableMovementByArrivedPosition(arrivedPosition);
        position = step(availableMovement, arrivedPosition);
    }

    private List<List<Movement>> generateMovements() {
        if (PalacePosition.isContains(position) && !PalacePosition.CENTER_POSITION.contains(position)) {
            List<List<Movement>> totalMovements = new ArrayList<>();
            totalMovements.addAll(MOVEMENTS);
            totalMovements.addAll(PALACE_MOVEMENTS);
            return totalMovements;
        }
        return MOVEMENTS;
    }

    public List<Movement> findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        List<List<Movement>> totalMovements = generateMovements();
        return totalMovements.stream()
                .filter(movement -> !arrivedPosition.isOutOfBoards() && step(movement, arrivedPosition).equals(arrivedPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    public List<Position> extractPathPositions(List<Movement> availableMovements, Position arrivedPosition) {
        List<Position> pathPositions = new ArrayList<>();
        int arrivedValue = 0;
        if (position.isHorizontalFromPosition(arrivedPosition)) {
            arrivedValue = position.calculateColumnDistance(arrivedPosition);
        }
        if (position.isVerticalFromPosition(arrivedPosition)) {
            arrivedValue = position.calculateRowDistance(arrivedPosition);
        }
        if (position.isCrossFromPosition(arrivedPosition)) {
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
    public boolean canNotJumpingOver() {
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
        return pieceType;
    }

    @Override
    public void receiveAttack() {
        isLive = false;
    }

    @Override
    public boolean isLive() {
        return isLive;
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
