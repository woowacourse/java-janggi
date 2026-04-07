package domain.piece.strategy;

import domain.board.Position;
import domain.path.Direction;
import domain.path.PathInfo;

import java.util.List;

public class GeneralMoveStrategy extends SingleStepLinearMoveStrategy {
    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        if (pathInfos.size() > 1) {
            throw new IllegalStateException("궁/사는 한 칸만 이동 가능합니다.");
        }
    }

    @Override
    public void validateMove(Direction direction, boolean isInPalace) {
        if (!isInPalace) {
            throw new IllegalArgumentException("궁/사는 궁성 내에서만 이동 가능합니다.");
        }
    }
}
