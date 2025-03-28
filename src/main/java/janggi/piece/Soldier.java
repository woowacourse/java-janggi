package janggi.piece;

import janggi.position.PalacePosition;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Soldier implements Piece {
    private final Team team;
    private final List<List<Movement>> movements;
    private final List<List<Movement>> palaceMovements;
    private Position position;
    private boolean isLive;
    private PieceType pieceType;

    public Soldier(Team team, Position position) {
        this.team = team;
        this.position = position;
        this.movements = choiceMovementsByTeam(team);
        this.palaceMovements = choicePalaceMovementsByTeam(team);
        this.isLive = true;
        this.pieceType = PieceType.SOLDIER;
    }

    private List<List<Movement>> choiceMovementsByTeam(Team team) {
        if (team == Team.CHO) {
            return List.of(
                    List.of(Movement.UP),
                    List.of(Movement.RIGHT),
                    List.of(Movement.LEFT)
            );
        }
        return List.of(
                List.of(Movement.DOWN),
                List.of(Movement.RIGHT),
                List.of(Movement.LEFT)
        );
    }

    private List<List<Movement>> choicePalaceMovementsByTeam(Team team) {
        if (team == Team.CHO) {
            return List.of(
                    List.of(Movement.RIGHT_UP),
                    List.of(Movement.LEFT_UP)
            );
        }
        return List.of(
                List.of(Movement.RIGHT_DOWN),
                List.of(Movement.LEFT_DOWN)
        );
    }

    @Override
    public void move(Position arrivedPosition) {
        List<Movement> availableMovement = findAvailableMovementByArrivedPosition(arrivedPosition);
        position = step(availableMovement);
    }

    // todo 졸/병 궁성 내 움직임 확인
    private List<List<Movement>> generateMovements() {
        if (PalacePosition.isContains(position)) {
            List<List<Movement>> totalMovements = new ArrayList<>();
            movements.addAll(movements);
            movements.addAll(palaceMovements);
            return totalMovements;
        }
        return movements;
    }

    public List<Movement> findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        List<List<Movement>> totalMovements = generateMovements();
        return totalMovements.stream()
                .filter(movement ->
                {
                    Position step = step(movement);
                    if (step.isOutOfPalace() && position.isCrossFromPosition(arrivedPosition)) {
                        return false;
                    }
                    return !arrivedPosition.isOutOfBoards() && step.equals(arrivedPosition);
                })
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
        Soldier soldier = (Soldier) o;
        return team == soldier.team && Objects.equals(position, soldier.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
