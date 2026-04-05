package domain.piece.strategy;

import domain.path.PathInfo;
import domain.board.Position;

import java.util.List;

public interface MoveStrategy {
    List<Position> getPath(Position departure, Position destination);

    default void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        boolean hasBlockingPiece = pathInfos.stream()
                .filter(path -> !path.position().equals(destination))
                .anyMatch(PathInfo::hasPiece);

        if (hasBlockingPiece) {
            throw new IllegalArgumentException("이동 경로에 있는 다른 기물을 뛰어넘을 수 없습니다.");
        }
    }
}
