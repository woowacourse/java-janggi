package janggi.domain.strategy;

import janggi.domain.Direction;
import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.PieceVO;
import janggi.domain.Position;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            addHorsePaths(current, baseDir, paths);
        }
        return paths;
    }

    private void addHorsePaths(Position current, Direction baseDir, Paths paths) {
        for (Direction diagonalDir : baseDir.getAdjacentDiagonals()) {
            createAndAddSequence(current, baseDir, diagonalDir, paths);
        }
    }

    private void createAndAddSequence(Position current, Direction baseDir, Direction diagonalDir, Paths paths) {
        try {
            Position step1 = baseDir.move(current);
            Position step2 = diagonalDir.move(step1);

            Path path = new Path();
            path.makePath(step1);
            path.makePath(step2);
            paths.addPath(path);
        } catch (IllegalArgumentException ignored) {

        }
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, PieceVO> boardState, PieceVO movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateHorsePath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateHorsePath(Path route, Map<Position, PieceVO> state, List<Position> dests, PieceVO me) {
        Iterator<Position> it = route.iterator();
        Position transit = it.next(); // 멱 (1번째)

        if (state.get(transit) == null) {
            addIfValid(it.next(), state, dests, me); // 도착지 (2번째)
        }
    }

    private void addIfValid(Position dest, Map<Position, PieceVO> state, List<Position> dests, PieceVO me) {
        PieceVO target = state.get(dest);
        if (target == null || !target.isSameSide(me)) {
            dests.add(dest);
        }
    }
}
