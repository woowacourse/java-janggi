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

public class Guard implements Piece {
    private static final List<Route> MOVEMENTS = List.of(
            new LimitedRoute(List.of(Movement.UP)),
            new LimitedRoute(List.of(Movement.DOWN)),
            new LimitedRoute(List.of(Movement.RIGHT)),
            new LimitedRoute(List.of(Movement.LEFT))
    );

    private static final List<Route> PALACE_MOVEMENTS = List.of(
            new LimitedRoute(List.of(Movement.RIGHT_UP)),
            new LimitedRoute(List.of(Movement.RIGHT_DOWN)),
            new LimitedRoute(List.of(Movement.LEFT_UP)),
            new LimitedRoute(List.of(Movement.LEFT_DOWN))
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
        Route availableRoute = findAvailableMovementByArrivedPosition(arrivedPosition);
        return new Guard(team, availableRoute.step(position, arrivedPosition), isLive);
    }

    private List<Route> generateMovements(Position arrivedPosition) {
        if (!arrivedPosition.isOutOfPalace() && PalacePosition.isContains(position)) {
            List<Route> totalMovements = new ArrayList<>();
            totalMovements.addAll(MOVEMENTS);
            totalMovements.addAll(PALACE_MOVEMENTS);
            return totalMovements;
        }
        return MOVEMENTS;
    }

    public Route findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        List<Route> totalMovements = generateMovements(arrivedPosition);
        return totalMovements.stream()
                .filter(route -> !arrivedPosition.isOutOfPalace() && !arrivedPosition.isOutOfBoards() &&  route.step(position, arrivedPosition).equals(arrivedPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    @Override
    public List<Position> extractPathPositions(Route availableMovements, Position arrivedPosition) {
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
