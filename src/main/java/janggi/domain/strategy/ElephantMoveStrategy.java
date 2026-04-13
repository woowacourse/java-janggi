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

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDirection : baseDirections) {
            addElephantPaths(current, baseDirection, paths);
        }

        return paths;
    }

    private void addElephantPaths(Position current, Direction baseDirection, Paths paths) {
        for (Direction diagonalDirection : baseDirection.getAdjacentDiagonals()) {
            Path.fromSequence(current, List.of(baseDirection, diagonalDirection, diagonalDirection))
                    .ifPresent(paths::addPath);
        }
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateElephantPath(route, boardState, destinations, movingPiece);
        }

        return destinations;
    }

    private void validateElephantPath(Path route, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Iterator<Position> iterator = route.iterator();
        Position firstStep = iterator.next();
        Position secondStep = iterator.next();

        Optional<Piece> pieceAtFirstStep = Optional.ofNullable(state.get(firstStep));
        Optional<Piece> pieceAtSecondStep = Optional.ofNullable(state.get(secondStep));

        if (pieceAtFirstStep.isEmpty() && pieceAtSecondStep.isEmpty()) {
            addIfValid(iterator.next(), state, destinations, me); // 최종 도착지
        }
    }

    private void addIfValid(Position destination, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Optional<Piece> target = Optional.ofNullable(state.get(destination));

        if (target.isEmpty() || !target.get().isSameSide(me)) {
            destinations.add(destination);
        }
    }
}
