package janggi.piece;

import janggi.dto.BoardPieceDto;
import janggi.movement.Movement;
import janggi.movement.Route;
import janggi.movement.UnLimitedRoute;
import janggi.position.PalacePosition;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Cannon implements Piece {
    private static final List<Route> MOVEMENTS = List.of(
            new UnLimitedRoute(Collections.nCopies(10, Movement.UP)),
            new UnLimitedRoute(Collections.nCopies(10, Movement.DOWN)),
            new UnLimitedRoute(Collections.nCopies(10, Movement.RIGHT)),
            new UnLimitedRoute(Collections.nCopies(10, Movement.LEFT))
    );

    private static final List<Route> PALACE_MOVEMENTS = List.of(
            new UnLimitedRoute(Collections.nCopies(2, Movement.RIGHT_UP)),
            new UnLimitedRoute(Collections.nCopies(2, Movement.RIGHT_DOWN)),
            new UnLimitedRoute(Collections.nCopies(2, Movement.LEFT_UP)),
            new UnLimitedRoute(Collections.nCopies(2, Movement.LEFT_DOWN))
    );

    private final Team team;
    private final Position position;
    private final boolean isLive;
    private final PieceType pieceType;

    public Cannon(Team team, Position position) {
        this.team = team;
        this.position = position;
        this.isLive = true;
        this.pieceType = PieceType.CANNON;
    }

    public Cannon(Team team, Position position, boolean isLive) {
        this.team = team;
        this.position = position;
        this.isLive = isLive;
        this.pieceType = PieceType.CANNON;
    }

    public Cannon(BoardPieceDto boardPieceDto) {
        this.team = boardPieceDto.team();
        this.position = boardPieceDto.position();
        this.isLive = boardPieceDto.isLive();
        this.pieceType = PieceType.CANNON;
    }

    @Override
    public Piece move(Position arrivedPosition) {
        Route availableRoute = findAvailableMovementByArrivedPosition(arrivedPosition);
        return new Cannon(team, availableRoute.step(position, arrivedPosition), isLive);
    }

    private List<Route> generateMovements() {
        if (PalacePosition.isContains(position) && !PalacePosition.CENTER_POSITION.contains(position)) {
            List<Route> totalMovements = new ArrayList<>();
            totalMovements.addAll(MOVEMENTS);
            totalMovements.addAll(PALACE_MOVEMENTS);
            return totalMovements;
        }
        return MOVEMENTS;
    }

    public Route findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        List<Route> totalRoutes = generateMovements();
        return totalRoutes.stream()
                .filter(route -> {
                    Position step = route.step(position, arrivedPosition);
                    if (step.isOutOfPalace() && position.isCrossFromPosition(arrivedPosition)) {
                        return false;
                    }
                    return  !arrivedPosition.isOutOfBoards() && step.equals(arrivedPosition);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    @Override
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
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Piece receiveAttack() {
        return new Cannon(team, position, false);
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
