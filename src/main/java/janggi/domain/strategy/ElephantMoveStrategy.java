package janggi.domain.strategy;

import janggi.domain.Direction;
import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.PieceVO;
import janggi.domain.Position;
import java.util.ArrayList;
import java.util.EnumSet;
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
    public List<Position> determineDestinations(Paths routes, Map<Position, PieceVO> boardState, PieceVO movingPieceVO) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateAndAddDestination(route, boardState, destinations);
        }
        return destinations;
    }

    private void validateAndAddDestination(Path route, Map<Position, PieceVO> boardState, List<Position> destinations) {
        int step = 0;
        for (Position pos : route) {
            PieceVO target = boardState.get(pos);

            if (step == 0 && target != null) { // Depth 2: 멱(첫 번째 좌표)에 기물이 있으면 즉시 차단
                break;
            }
            if (step == 1 && target != null) { // Depth 2: 멱(두 번째 좌표)에 기물이 있으면 즉시 차단
                break;
            }
            if (step == 2 && target == null) { // 최종 목적지가 비어있으면 이동 가능
                destinations.add(pos);
            }
            step++;
        }
    }
}
