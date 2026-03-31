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
            createAndAddSequence(current, baseDirection, diagonalDirection, paths);
        }
    }

    private void createAndAddSequence(Position current, Direction baseDirection, Direction diagonalDirection,
                                      Paths paths) {
        // 직선 이동이 가능한지 체크
        if (!current.canMove(baseDirection)) {
            return;
        }
        Position step1 = baseDirection.move(current);

        // 꺾이는 대각선 이동이 가능한지 체크
        if (!step1.canMove(diagonalDirection)) {
            return;
        }
        Position step2 = diagonalDirection.move(step1);

        Path path = new Path();
        path.makePath(step1);
        path.makePath(step2);
        paths.addPath(path);
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
        Iterator<Position> it = route.iterator();
        Position transit = it.next(); // 멱 (1번째)

        if (state.get(transit) == null) {
            addIfValid(it.next(), state, destinations, me); // 도착지 (2번째)
        }
    }

    private void addIfValid(Position dest, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Piece target = state.get(dest);
        if (target == null || !target.isSameSide(me)) {
            destinations.add(dest);
        }
    }
}
