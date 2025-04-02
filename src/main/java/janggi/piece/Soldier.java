package janggi.piece;

import janggi.dto.BoardPieceDto;
import janggi.movement.LimitedRoute;
import janggi.movement.Movement;
import janggi.movement.Route;
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
    private final List<Route> movements;
    private final List<Route> palaceMovements;

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

    private List<Route> choiceMovementsByTeam(Team team) {
        if (team == Team.CHO) {
            return List.of(
                    new LimitedRoute(List.of(Movement.UP)),
                    new LimitedRoute(List.of(Movement.RIGHT)),
                    new LimitedRoute(List.of(Movement.LEFT))
            );
        }
        return List.of(
                new LimitedRoute(List.of(Movement.DOWN)),
                new LimitedRoute(List.of(Movement.RIGHT)),
                new LimitedRoute(List.of(Movement.LEFT))
        );
    }

    private List<Route> choicePalaceMovementsByTeam(Team team) {
        if (team == Team.CHO) {
            return List.of(
                    new LimitedRoute(List.of(Movement.RIGHT_UP)),
                    new LimitedRoute(List.of(Movement.LEFT_UP))
            );
        }
        return List.of(
                new LimitedRoute(List.of(Movement.RIGHT_DOWN)),
                new LimitedRoute(List.of(Movement.LEFT_DOWN))
        );
    }

    @Override
    public Piece move(Position arrivedPosition) {
        Route availableRoute = findAvailableMovementByArrivedPosition(arrivedPosition);
        return new Soldier(team, availableRoute.step(position, arrivedPosition), isLive);
    }

    private List<Route> generateMovements() {
        if (PalacePosition.isContains(position)) {
            List<Route> totalMovements = new ArrayList<>();
            totalMovements.addAll(movements);
            totalMovements.addAll(palaceMovements);
            return totalMovements;
        }
        return movements;
    }

    public Route findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        List<Route> totalMovements = generateMovements();
        return totalMovements.stream()
                .filter(route ->
                {
                    Position step = route.step(position, arrivedPosition);
                    if (step.isOutOfPalace() && position.isCrossFromPosition(arrivedPosition)) {
                        return false;
                    }
                    return !arrivedPosition.isOutOfBoards() && step.equals(arrivedPosition);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    @Override
    public List<Position> extractPathPositions(Route availableRoute, Position arrivedPosition) {
        return List.of();
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
