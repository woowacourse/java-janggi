package janggi.piece;

import janggi.dto.BoardPieceDto;
import janggi.position.PalacePosition;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guard implements Piece {
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
    private final Position position;
    private final boolean isLive;
    private final PieceType pieceType;

    public Guard(Team team, Position position) {
        this.team = team;
        this.position = position;
        this.isLive = true;
        this.pieceType = PieceType.GUARD;
    }

    public Guard(Team team, Position position, boolean isLive) {
        this.team = team;
        this.position = position;
        this.isLive = isLive;
        this.pieceType = PieceType.GUARD;
    }

    public Guard(BoardPieceDto boardPieceDto) {
        this.team = boardPieceDto.team();
        this.position = boardPieceDto.position();
        this.isLive = boardPieceDto.isLive();
        this.pieceType = PieceType.GUARD;
    }

    @Override
    public Piece move(Position arrivedPosition) {
        List<Movement> availableMovement = findAvailableMovementByArrivedPosition(arrivedPosition);
        return new Guard(team, step(availableMovement), isLive);
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
    public Piece receiveAttack() {
        return new Guard(team, position, false);
    }

    @Override
    public boolean isLive() {
        return isLive;
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
