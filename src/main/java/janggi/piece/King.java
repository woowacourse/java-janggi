package janggi.piece;

import janggi.position.PalacePosition;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class King implements Piece {
    private static final List<List<Movement>> MOVEMENTS = List.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.RIGHT),
            List.of(Movement.LEFT)
    );

    private static final List<List<Movement>> PALACE_MOVEMENTS = List.of(
            List.of(Movement.RIGHT_UP),
            List.of(Movement.RIGHT_DOWN),
            List.of(Movement.LEFT_UP),
            List.of(Movement.LEFT_DOWN)
    );

    private final Team team;
    private Position position;
    private boolean isLive;
    private PieceType pieceType;

    public King(Team team, Position position) {
        this.team = team;
        this.position = position;
        this.isLive = true;
        this.pieceType = PieceType.KING;
    }

    public King(Team team, Position position, boolean isLive) {
        this.team = team;
        this.position = position;
        this.isLive = isLive;
        this.pieceType = PieceType.KING;
    }

    @Override
    public void move(Position arrivedPosition) {
        List<Movement> availableMovement = findAvailableMovementByArrivedPosition(arrivedPosition);
        position = step(availableMovement);
    }

    private List<List<Movement>> generateMovements(Position arrivedPosition) {
        if (!arrivedPosition.isOutOfPalace() && PalacePosition.isContains(position)) {
            List<List<Movement>> totalMovements = new ArrayList<>();
            totalMovements.addAll(MOVEMENTS);
            totalMovements.addAll(PALACE_MOVEMENTS);
            return totalMovements;
        }
        return MOVEMENTS;
    }

    public List<Movement> findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        List<List<Movement>> totalMovements = generateMovements(arrivedPosition);
        return totalMovements.stream()
                .filter(movement -> !arrivedPosition.isOutOfPalace() && !arrivedPosition.isOutOfBoards() && step(movement).equals(arrivedPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    @Override
    public List<Position> extractPathPositions(List<Movement> availableMovements, Position arrivedPosition) {
        return List.of();
    }

    private Position step(List<Movement> movements) {
        Position reachablePosition = position;
        for (Movement movement : movements) {
            reachablePosition = movement.move(reachablePosition);
        }
        return reachablePosition;
    }

    @Override
    public boolean isObstacle(List<Position> pathPositions) {
        return pathPositions.stream()
                .anyMatch(pathPosition -> pathPosition.equals(position));
    }

    @Override
    public boolean canNotJumpingOver() {
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
    public PieceType getPieceType() {
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
        King king = (King) o;
        return team == king.team && Objects.equals(position, king.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
