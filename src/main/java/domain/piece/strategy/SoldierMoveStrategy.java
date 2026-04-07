package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathInfo;
import domain.path.Direction;

import java.util.ArrayList;
import java.util.List;

public class SoldierMoveStrategy extends SingleStepLinearMoveStrategy {
    private final Direction forwardDirection;

    public SoldierMoveStrategy(Direction forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    @Override
    protected void validateMove(Direction direction, boolean isInPalace) {
        List<Direction> allowedDirections = new ArrayList<>(List.of(Direction.LEFT, Direction.RIGHT, forwardDirection));
        if (isInPalace){
            allowedDirections.add(direction);
        }

        if (!allowedDirections.contains(direction)) {
            throw new IllegalArgumentException("졸/병은 후퇴할 수 없습니다.");
        }
    }

    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        if (pathInfos.size() > 1) {
            throw new IllegalStateException("졸/병은 한 칸만 이동 가능합니다.");
        }
    }
}
