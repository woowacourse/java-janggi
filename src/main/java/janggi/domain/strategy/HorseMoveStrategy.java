package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.piece.Piece;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDirection : baseDirections) {
            addHorsePaths(current, baseDirection, paths);
        }
        return paths;
    }

    private void addHorsePaths(Position current, Direction baseDirection, Paths paths) {
        for (Direction diagonalDirection : baseDirection.getAdjacentDiagonals()) {
            Path.fromSequence(current, List.of(baseDirection, diagonalDirection))
                    .ifPresent(paths::addPath);
        }
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateHorsePath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateHorsePath(Path route, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Iterator<Position> iterator = route.iterator();
        Position firstStep = iterator.next(); // 멱 (1번째)
        Optional<Piece> pieceAtFirstStep = Optional.ofNullable(state.get(firstStep));

        if (pieceAtFirstStep.isEmpty()) {
            addIfValid(iterator.next(), state, destinations, me); // 도착지 (2번째)
        }
    }

    private void addIfValid(Position destination, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Optional<Piece> target = Optional.ofNullable(state.get(destination));

        if (target.isEmpty() || !target.get().isSameSide(me)) {
            destinations.add(destination);
        }
    }
}
