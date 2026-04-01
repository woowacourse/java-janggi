package strategy.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;

public abstract class MoveStrategy {

    public abstract List<MovePath> getPaths(Piece piece);

    public List<Route> makeRoutes(Position curPos, Piece piece) {
        List<Route> validRoutes = new ArrayList<>();
        List<MovePath> paths = getPaths(piece);

        for (MovePath path : paths) {
            routeIfWithinBoard(curPos, path).ifPresent(validRoutes::add);
        }

        return validRoutes;
    }

    private Optional<Route> routeIfWithinBoard(Position start, MovePath path) {
        List<Direction> steps = path.steps();
        List<Position> intermediates = new ArrayList<>();
        Position currentPos = start;

        for (int i = 0; i < steps.size(); i++) {
            currentPos = currentPos.next(steps.get(i));
            if (!currentPos.isInsideBoard()) {
                return Optional.empty();
            }
            if (i < steps.size() - 1) {
                intermediates.add(currentPos);
            }
        }

        return Optional.of(new Route(start, currentPos, intermediates));
    }

    public abstract boolean canMove(Route route, List<Piece> blockingPieces, Piece pieceAtDestination, TeamColor myTeam);

}
