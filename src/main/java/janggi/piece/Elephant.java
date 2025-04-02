package janggi.piece;

import janggi.dto.BoardPieceDto;
import janggi.movement.LimitedRoute;
import janggi.movement.Movement;
import janggi.movement.Route;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Elephant implements Piece {
    private static final List<Route> MOVEMENTS = List.of(
            new LimitedRoute(List.of(Movement.UP, Movement.RIGHT_UP, Movement.RIGHT_UP)),
            new LimitedRoute(List.of(Movement.UP, Movement.LEFT_UP, Movement.LEFT_UP)),
            new LimitedRoute(List.of(Movement.LEFT, Movement.LEFT_UP, Movement.LEFT_UP)),
            new LimitedRoute(List.of(Movement.LEFT, Movement.LEFT_DOWN, Movement.LEFT_DOWN)),
            new LimitedRoute(List.of(Movement.RIGHT, Movement.RIGHT_UP, Movement.RIGHT_UP)),
            new LimitedRoute(List.of(Movement.RIGHT, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN)),
            new LimitedRoute(List.of(Movement.DOWN, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN)),
            new LimitedRoute(List.of(Movement.DOWN, Movement.LEFT_DOWN, Movement.LEFT_DOWN))
    );

    private final Team team;
    private final Position position;
    private final boolean isLive;
    private final PieceType pieceType;

    public Elephant(Team team, Position position) {
        this.team = team;
        this.position = position;
        this.isLive = true;
        this.pieceType = PieceType.ELEPHANT;
    }

    public Elephant(Team team, Position position, boolean isLive) {
        this.team = team;
        this.position = position;
        this.isLive = isLive;
        this.pieceType = PieceType.ELEPHANT;
    }

    public Elephant(BoardPieceDto boardPieceDto) {
        this.team = boardPieceDto.team();
        this.position = boardPieceDto.position();
        this.isLive = boardPieceDto.isLive();
        this.pieceType = PieceType.ELEPHANT;
    }

    @Override
    public Piece move(Position arrivedPosition) {
        Route availableRoute = findAvailableMovementByArrivedPosition(arrivedPosition);
        return new Elephant(team,availableRoute.step(position, arrivedPosition),isLive);
    }

    public Route findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        return MOVEMENTS.stream()
                .filter(route -> !arrivedPosition.isOutOfBoards() && route.step(position, arrivedPosition).equals(arrivedPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    public List<Position> extractPathPositions(Route availableRoute, Position arrivedPosition) {
        return availableRoute.extractPathPositions(position, arrivedPosition);
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
        return new Elephant(team, position, false);
    }

    @Override
    public boolean isLive() {
        return isLive;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Elephant elephant = (Elephant) o;
        return team == elephant.team && Objects.equals(position, elephant.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
