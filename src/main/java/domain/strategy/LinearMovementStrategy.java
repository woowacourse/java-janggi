package domain.strategy;

import domain.movement.Direction;
import domain.vo.Position;
import domain.movement.LinearMovement;
import domain.movement.Movement;
import domain.movement.PathMovement;
import domain.path.Path;
import java.util.List;

public class LinearMovementStrategy implements PieceMoveStrategy {

    private static final String INVALID_TARGET_POSITION = "이동할 수 없는 목적지입니다.";

    @Override
    public List<Position> findRoute(List<Path> paths, Position source, Position target) {
        Movement linearMovement = new LinearMovement();
        Movement pathMovement = new PathMovement();

        for (Path path : paths) {
            List<Direction> route = path.getPath();
            Movement movement = linearMovement;
            if (isDiagonalOnly(route)) {
                movement = pathMovement;
            }
            List<Position> positions = movement.buildRoute(route, source, target);
            if (!positions.isEmpty() && positions.getLast().equals(target)) {
                return positions;
            }
        }
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    private boolean isDiagonalOnly(List<Direction> route) {
        return !route.isEmpty() && route.stream().allMatch(Direction::isDiagonal);
    }
}
