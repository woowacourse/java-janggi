package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import java.util.stream.Stream;

public class Cannon implements Piece {
    private final Team team;

    public Cannon(Team team) {
        this.team = team;
    }

    @Override
    public List<Route> calculateRoutes(Position start) {
        List<Position> positions = calculateEndPoints(start);
        return positions.stream()
                .map(end -> calculateRoute(start, end))
                .filter(route -> route.length() != 0)
                .toList();
    }

    private List<Position> calculateEndPoints(Position start) {
        List<Position> parallelPositions = start.creatParallelPosition(0, Position.COLUMN_MAX);
        List<Position> verticalPositions = start.createVerticalPosition(0, Position.ROW_MAX);
        return Stream.concat(parallelPositions.stream(), verticalPositions.stream())
                .toList();
    }

    private Route calculateRoute(Position start, Position end) {
        if (start.isParallel(end)) {
            return Route.of(start.creatParallelPosition(start.getColumn(), end.getColumn()));
        }
        return Route.of(start.createVerticalPosition(start.getRow(), end.getRow()));
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
