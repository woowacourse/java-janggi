package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathInfo;
import domain.piece.PieceType;

import java.util.List;

public class CannonMoveStrategy extends LinearMoveStrategy {
    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        if (pathInfos.size() != 2) {
            throw new IllegalArgumentException("포는 반드시 하나의 기물만을 이동할 수 있습니다.");
        }
        if (pathInfos.stream().anyMatch(pathInfo -> pathInfo.isPieceType(PieceType.CANNON))) {
            throw new IllegalArgumentException("포는 포를 넘거나 잡을 수 없습니다.");
        }
    }
}
