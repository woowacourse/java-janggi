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

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            addElephantPaths(current, baseDir, paths);
        }
        return paths;
    }

    private void addElephantPaths(Position current, Direction baseDir, Paths paths) {
        for (Direction diagonalDir : baseDir.getAdjacentDiagonals()) {
            createAndAddSequence(current, baseDir, diagonalDir, paths);
        }
    }

    private void createAndAddSequence(Position current, Direction baseDir, Direction diagonalDir, Paths paths) {
        try {
            Position step1 = baseDir.move(current);
            Position step2 = diagonalDir.move(step1);
            Position step3 = diagonalDir.move(step2);

            Path path = new Path();
            path.makePath(step1);
            path.makePath(step2);
            path.makePath(step3);
            paths.addPath(path);
        } catch (IllegalArgumentException ignored) {
            // 보드 밖으로 나가는 좌표가 하나라도 발생하면 해당 경로는 물리적으로 불가하므로 폐기
        }
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, PieceVO> boardState, PieceVO movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateElephantPath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateElephantPath(Path route, Map<Position, PieceVO> state, List<Position> dests, PieceVO me) {
        Iterator<Position> it = route.iterator();
        Position transit1 = it.next();
        Position transit2 = it.next();

        if (state.get(transit1) == null && state.get(transit2) == null) {
            addIfValid(it.next(), state, dests, me); // 최종 도착지
        }
    }

    private void addIfValid(Position dest, Map<Position, PieceVO> state, List<Position> dests, PieceVO me) {
        PieceVO target = state.get(dest);
        if (target == null || target.isSameSide(me)) dests.add(dest);
    }
}
