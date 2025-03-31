package janggi.piece;

import janggi.dto.BoardPieceDto;
import janggi.position.PalacePosition;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Soldier implements Piece {
    private final Team team;
    private final Position position;
    private final boolean isLive;
    private final PieceType pieceType;
    private final List<List<Movement>> movements;
    private final List<List<Movement>> palaceMovements;

    public Soldier(Team team, Position position) {
        this.team = team;
        this.position = position;
        this.isLive = true;
        this.pieceType = PieceType.SOLDIER;
        this.movements = choiceMovementsByTeam(team);
        this.palaceMovements = choicePalaceMovementsByTeam(team);
    }

    public Soldier(Team team, Position position, boolean isLive) {
        this.team = team;
        this.position = position;
        this.isLive = isLive;
        this.pieceType = PieceType.SOLDIER;
        this.movements = choiceMovementsByTeam(team);
        this.palaceMovements = choicePalaceMovementsByTeam(team);
    }

    public Soldier(BoardPieceDto boardPieceDto) {
        this.team = boardPieceDto.team();
        this.position = boardPieceDto.position();
        this.isLive = boardPieceDto.isLive();
        this.pieceType = PieceType.SOLDIER;
        this.movements = choiceMovementsByTeam(team);
        this.palaceMovements = choicePalaceMovementsByTeam(team);
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
    public Piece move(Position arrivedPosition) {
        List<Movement> availableMovement = findAvailableMovementByArrivedPosition(arrivedPosition);
        return new Soldier(team, step(availableMovement), isLive);
    }

    // todo 졸/병 궁성 내 움직임 확인
    private List<List<Movement>> generateMovements() {
        if (PalacePosition.isContains(position)) {
            List<List<Movement>> totalMovements = new ArrayList<>();
            totalMovements.addAll(movements);
            totalMovements.addAll(palaceMovements);
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
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Piece receiveAttack() {
        return new Soldier(team, position, false);
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
