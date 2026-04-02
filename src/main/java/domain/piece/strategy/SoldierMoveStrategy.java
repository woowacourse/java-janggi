package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathGenerator;
import domain.path.PathInfo;
import domain.piece.Direction;

import java.util.List;

public class SoldierMoveStrategy implements MoveStrategy {
    private final Direction forwardDirection;

    public SoldierMoveStrategy(Direction forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    @Override
    public List<Position> getPath(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        if (deltaX != 0 && deltaY != 0) {
            throw new IllegalArgumentException("졸/병은 직선 방향으로만 이동할 수 있습니다.");
        }

        if (deltaX > 1 || deltaY > 1) {
            throw new IllegalArgumentException("졸/병은 직선 방향으로 한 칸만 이동 가능합니다.");
        }

        Direction direction = Direction.decideDirection(deltaX, deltaY);
        List<Direction> allowedDirections = List.of(Direction.LEFT, Direction.RIGHT, forwardDirection);

        if (!allowedDirections.contains(direction)) {
            throw new IllegalArgumentException("졸/병은 후퇴할 수 없습니다.");
        }

        return PathGenerator.generateStraightPath(departure, destination, direction);
    }

    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position departure, Position destination) {
        boolean hasBlockingPiece = pathInfos.stream()
                .filter(path -> !path.position().equals(destination))
                .anyMatch(PathInfo::hasPiece);

        if (hasBlockingPiece) {
            throw new IllegalArgumentException("이동 경로에 있는 다른 기물을 뛰어넘을 수 없습니다.");
        }
    }
}
