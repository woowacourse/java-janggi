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

public class Chariot implements Piece {
    private static final int POSSIBLE_MOVEMENT_COUNT = 10;

    private static final List<Route> MOVEMENTS = List.of(
            new UnLimitedRoute(Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.UP)),
            new UnLimitedRoute(Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.DOWN)),
            new UnLimitedRoute(Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.RIGHT)),
            new UnLimitedRoute(Collections.nCopies(POSSIBLE_MOVEMENT_COUNT, Movement.LEFT))
    );

    private static final List<Route> PALACE_MOVEMENTS = List.of(
            new UnLimitedRoute(Collections.nCopies(2, Movement.RIGHT_UP)),
            new UnLimitedRoute(Collections.nCopies(2, Movement.RIGHT_DOWN)),
            new UnLimitedRoute(Collections.nCopies(2, Movement.LEFT_UP)),
            new UnLimitedRoute(Collections.nCopies(2, Movement.LEFT_DOWN)),
            new UnLimitedRoute(List.of(Movement.RIGHT_UP)),
            new UnLimitedRoute(List.of(Movement.RIGHT_DOWN)),
            new UnLimitedRoute(List.of(Movement.LEFT_UP)),
            new UnLimitedRoute(List.of(Movement.LEFT_DOWN))
    );

    private final Team team;
    private final Position position;
    private final boolean isLive;
    private final PieceType pieceType;

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
        Route availableRoute = findAvailableMovementByArrivedPosition(arrivedPosition);
        return new Chariot(team, availableRoute.step(position, arrivedPosition), isLive);
    }

    private List<Route> generateMovements() {
        List<Route> totalMovements = new ArrayList<>();
        if (PalacePosition.isContains(position)) {
            totalMovements.addAll(MOVEMENTS);
            totalMovements.addAll(PALACE_MOVEMENTS);
            return totalMovements;
        }
        return MOVEMENTS;
    }

    public Route findAvailableMovementByArrivedPosition(Position arrivedPosition) {
        List<Route> totalMovements = generateMovements();
        return totalMovements.stream()
                .filter(route -> {
                    Position step = route.step(position, arrivedPosition);
                    if (step.isOutOfPalace() && position.isCrossFromPosition(arrivedPosition)) {
                        return false;
                    }
                    return  !arrivedPosition.isOutOfBoards() && step.equals(arrivedPosition);
                    }
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    public List<Position> extractPathPositions(Route availableRoute, Position arrivedPosition) {
        return availableRoute.extractPathPositions(position,arrivedPosition);
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
