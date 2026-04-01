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
        for (Direction diagonalDir : baseDirection.getAdjacentDiagonals()) {
            createAndAddSequence(current, baseDirection, diagonalDir, paths);
        }
    }

    private void createAndAddSequence(Position current, Direction baseDirection, Direction diagonalDirection,
                                      Paths paths) {
        // 직선 이동 가능한지 체크
        if (!current.canMove(baseDirection)) {
            return;
        }
        Position step1 = baseDirection.move(current);

        // 첫 번째 대각선 이동 가능한지 체크
        if (!step1.canMove(diagonalDirection)) {
            return;
        }
        Position step2 = diagonalDirection.move(step1);

        // 두 번째 대각선 이동 가능한지 체크
        if (!step2.canMove(diagonalDirection)) {
            return;
        }
        Position step3 = diagonalDirection.move(step2);

        Path path = new Path();
        path.add(step1);
        path.add(step2);
        path.add(step3);
        paths.addPath(path);
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
        Iterator<Position> it = route.iterator();
        Position transit1 = it.next();
        Position transit2 = it.next();

        if (state.get(transit1) == null && state.get(transit2) == null) {
            addIfValid(it.next(), state, destinations, me); // 최종 도착지
        }
    }

    private void addIfValid(Position dest, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Piece target = state.get(dest);
        if (target == null || !target.isSameSide(me)) {
            destinations.add(dest);
        }
    }
}
