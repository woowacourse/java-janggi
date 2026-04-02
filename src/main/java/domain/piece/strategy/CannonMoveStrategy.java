package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathGenerator;
import domain.path.PathInfo;
import domain.path.Direction;
import domain.piece.PieceType;

import java.util.List;

public class CannonMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> getPath(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        if (deltaX != 0 && deltaY != 0) {
            throw new IllegalArgumentException("포는 직선 방향으로만 이동할 수 있습니다.");
        }

        Direction direction = Direction.decideDirection(deltaX, deltaY);
        return PathGenerator.generateStraightPath(departure, destination, direction);
    }

    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        if (pathInfos.stream().anyMatch(path -> path.isSamePieceType(PieceType.CANNON))) {
            throw new IllegalArgumentException("포는 포를 넘거나 잡을 수 없습니다.");
        }
    }
}
