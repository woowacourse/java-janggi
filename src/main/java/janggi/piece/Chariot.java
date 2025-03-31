package janggi.piece;

import janggi.dto.BoardPieceDto;
import janggi.position.PalacePosition;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Chariot implements Piece {
    private static final int POSSIBLE_MOVEMENT_COUNT = 10;
    private static final List<List<Movement>> MOVEMENTS = List.of(
            Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.UP),
            Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.DOWN),
            Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.RIGHT),
            Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.LEFT)
    );

    private static final List<List<Movement>> PALACE_MOVEMENTS = List.of(
            List.of(Movement.RIGHT_UP),
            List.of(Movement.RIGHT_DOWN),
            List.of(Movement.LEFT_UP),
            List.of(Movement.LEFT_DOWN)
    );

    private final Team team;
    private final Position position;
    private boolean isLive;
    private PieceType pieceType;

    public Chariot(Team team, Position position) {
        this.team = team;
        this.position = position;
        this.isLive = true;
        this.pieceType = PieceType.CHARIOT;
    }

    public Chariot(Team team, Position position, boolean isLive) {
        this.team = team;
        this.position = position;
        this.isLive = isLive;
        this.pieceType = PieceType.CHARIOT;
    }

    public Chariot(BoardPieceDto boardPieceDto) {
        this.team = boardPieceDto.team();
        this.position = boardPieceDto.position();
        this.isLive = boardPieceDto.isLive();
        this.pieceType = PieceType.CHARIOT;
    }

    @Override
    public Piece move(Position arrivedPosition) {
        List<Movement> availableMovement = findAvailableMovementByArrivedPosition(arrivedPosition);
        return new Chariot(team, step(availableMovement, arrivedPosition), isLive);
    }

    private List<List<Movement>> generateMovements() {
        List<List<Movement>> totalMovements = new ArrayList<>();
        if (PalacePosition.isContains(position)) {
            totalMovements.addAll(MOVEMENTS);
            totalMovements.addAll(PALACE_MOVEMENTS);
            return totalMovements;
        }
        return MOVEMENTS;
    }

    public List<Movement> findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        List<List<Movement>> totalMovements = generateMovements();
        return totalMovements.stream()
                .filter(movement -> {
                    Position step = step(movement, arrivedPosition);
                    if (step.isOutOfPalace() && position.isCrossFromPosition(arrivedPosition)) {
                        return false;
                    }
                    return  !arrivedPosition.isOutOfBoards() && step.equals(arrivedPosition);
                    }
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    public List<Position> extractPathPositions(List<Movement> availableMovements, Position arrivedPosition) {
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

        if (position.isCrossFromPosition(arrivedPosition)) {
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
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Piece receiveAttack() {
        isLive = false;
        return new Chariot(team, position, false);
    }

    @Override
    public boolean isLive() {
        return isLive;
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
