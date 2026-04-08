package domain.strategy;

import domain.vo.Position;
import domain.movement.PathMovement;
import domain.path.Path;
import java.util.List;

public class PathMovementStrategy implements PieceMoveStrategy {

    private static final String INVALID_TARGET_POSITION = "이동할 수 없는 목적지입니다.";

    @Override
    public List<Position> findRoute(List<Path> paths, Position source, Position target) {
        PathMovement pathMovement = new PathMovement();
        for (Path path : paths) {
            List<Position> positions = pathMovement.buildRoute(path.getPath(), source, target);
            if (!positions.isEmpty() && positions.getLast().equals(target)) {
                return positions;
            }
        }
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }
}
