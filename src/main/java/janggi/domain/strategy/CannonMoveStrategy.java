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
        for (Direction baseDirection : baseDirections) {
            addCannonPath(current, baseDirection, paths);
        }
        return paths;
    }

    private void addCannonPath(Position current, Direction baseDirection, Paths paths) {
        Path path = new Path();

        // 첫 이동 시도
        Optional<Position> possibleNext = current.tryMove(baseDirection);

        // 이동에 성공한 경우, 계속 반복
        while (possibleNext.isPresent()) {
            Position next = possibleNext.get();
            path.add(next);

            // 현재 위치에서 같은 방향으로 또 이동 시도
            possibleNext = next.tryMove(baseDirection);
        }

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
        Piece firstPiece = findFirstPiece(iterator, state);
        return isValidBridge(firstPiece);
    }

    private Piece findFirstPiece(Iterator<Position> iterator, Map<Position, Piece> state) {
        while (iterator.hasNext()) {
            Piece piece = state.get(iterator.next());
            if (piece != null) {
                return piece;
            }
        }
        return null;
    }

    private boolean isValidBridge(Piece piece) {
        return (piece != null) && !piece.isCannon();
    }

    private void findDestinationsAfterJump(Iterator<Position> iterator, Map<Position, Piece> state,
                                           List<Position> destinations, Piece me) {
        while (iterator.hasNext()) {
            Position position = iterator.next();
            Piece target = state.get(position);

            if (target == null) {
                destinations.add(position);
                continue;
            }

            addTargetIfCapturable(position, target, destinations, me);
            return;
        }
    }

    private void addTargetIfCapturable(Position position, Piece target, List<Position> destinations, Piece me) {
        if (!target.isCannon() && !target.isSameSide(me)) {
            destinations.add(position);
        }
    }
}
