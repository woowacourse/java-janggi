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

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();

        // 기본 직선 경로
        for (Direction baseDirection : baseDirections) {
            addCannonPath(current, baseDirection, paths);
        }

        // 궁성 내 대각선 경로
        if (current.isPalaceCorner()) {
            for (Direction diagonalDirection : current.getValidPalaceDiagonals()) {
                Path.fromSequence(current, List.of(diagonalDirection, diagonalDirection))
                        .ifPresent(paths::addPath);
            }
        }

        return paths;
    }

    private void addCannonPath(Position current, Direction baseDirection, Paths paths) {
        Path path = Path.fromContinuousMove(current, baseDirection);
        if (!path.isEmpty()) {
            paths.addPath(path);
        }
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateCannonPath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateCannonPath(Path route, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Iterator<Position> iterator = route.iterator();
        if (findBridge(iterator, state)) {
            findDestinationsAfterJump(iterator, state, destinations, me);
        }
    }

    private boolean findBridge(Iterator<Position> iterator, Map<Position, Piece> state) {
        Optional<Piece> firstPiece = findFirstPiece(iterator, state);

        return firstPiece
                .map(this::isValidBridge)
                .orElse(false);
    }

    private Optional<Piece> findFirstPiece(Iterator<Position> iterator, Map<Position, Piece> state) {
        while (iterator.hasNext()) {
            Optional<Piece> piece = Optional.ofNullable(state.get(iterator.next()));

            if (piece.isPresent()) {
                return piece;
            }
        }
        return Optional.empty();
    }

    private boolean isValidBridge(Piece piece) {
        return !piece.isCannon();
    }

    private void findDestinationsAfterJump(Iterator<Position> iterator, Map<Position, Piece> state,
                                           List<Position> destinations, Piece me) {
        while (iterator.hasNext()) {
            Position position = iterator.next();
            Optional<Piece> target = Optional.ofNullable(state.get(position));

            if (target.isEmpty()) {
                destinations.add(position);
                continue;
            }

            addTargetIfCapturable(position, target.get(), destinations, me);
            return;
        }
    }

    private void addTargetIfCapturable(Position position, Piece target, List<Position> destinations, Piece me) {
        if (!target.isCannon() && !target.isSameSide(me)) {
            destinations.add(position);
        }
    }
}
