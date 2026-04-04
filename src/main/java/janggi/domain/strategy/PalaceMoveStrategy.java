package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PalaceMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDirection : baseDirections) {
            addPalacePath(current, baseDirection, paths);
        }
        return paths;
    }

    private void addPalacePath(Position current, Direction baseDirection, Paths paths) {
        Path.fromSequence(current, List.of(baseDirection))
                .filter(path -> isValidPalacePath(current, path, baseDirection))
                .ifPresent(paths::addPath);
    }

    private boolean isValidPalacePath(Position current, Path path, Direction baseDirection) {
        Position next = path.iterator().next();

        if (!next.isPalace()) {
            return false;
        }

        if (baseDirection.isDiagonal()) {
            return current.isPalaceCenter() || next.isPalaceCenter();
        }

        return true;
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validatePalacePath(route, boardState, destinations, movingPiece);
        }

        return destinations;
    }

    private void validatePalacePath(Path route, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Position destination = route.iterator().next();
        Optional<Piece> target = Optional.ofNullable(state.get(destination));
        if (target.isEmpty() || !target.get().isSameSide(me)) {
            destinations.add(destination);
        }
    }
}
