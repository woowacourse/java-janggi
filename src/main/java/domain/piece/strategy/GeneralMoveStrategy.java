package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathGenerator;
import domain.path.PathInfo;
import domain.path.Direction;

import java.util.List;

public class GeneralMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> getPath(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        if (deltaX != 0 && deltaY != 0) {
            throw new IllegalArgumentException("궁/사는 직선 방향으로만 이동할 수 있습니다.");
        }

        if (Math.abs(deltaX) + Math.abs(deltaY) != 1) {
            throw new IllegalArgumentException("궁/사는 직선 방향으로 한 칸만 이동 가능합니다.");
        }

        Direction direction = Direction.decideDirection(deltaX, deltaY);
        return PathGenerator.generateStraightPath(departure, destination, direction);
    }

    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        if(pathInfos.size() > 1){
            throw new IllegalStateException("궁/사는 한 칸만 이동 가능합니다.");
        }
    }
}
