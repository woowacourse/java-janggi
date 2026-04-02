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
            createAndAddSequence(current, baseDirection, diagonalDirection, paths);
        }
    }

    private void createAndAddSequence(Position current, Direction baseDirection, Direction diagonalDirection,
                                      Paths paths) {
        // 직선 이동이 가능한지 체크
        current.tryMove(baseDirection).ifPresent(step1 -> {
            // 첫 번째 대각선 이동 가능한지 체크
            step1.tryMove(diagonalDirection).ifPresent(step2 -> {
                // 두 번째 대각선 이동 가능한지 체크
                step2.tryMove(diagonalDirection).ifPresent(step3 -> {
                    Path path = new Path();
                    path.add(step1);
                    path.add(step2);
                    path.add(step3);
                    paths.addPath(path);
                });
            });
        });
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
        Position transit1 = iterator.next();
        Position transit2 = iterator.next();

        if (state.get(transit1) == null && state.get(transit2) == null) {
            addIfValid(iterator.next(), state, destinations, me); // 최종 도착지
        }
    }

    private void addIfValid(Position destination, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Piece target = state.get(destination);
        if (target == null || !target.isSameSide(me)) {
            destinations.add(destination);
        }
    }
}
