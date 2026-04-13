package domain.path;

import domain.board.Position;
import domain.piece.PieceType;

import java.util.List;

public class PathInfos {
    private final List<PathInfo> pathInfos;

    public PathInfos(List<PathInfo> pathInfos) {
        this.pathInfos = pathInfos;
    }

    public void validateOnlyOneStep() {
        if (pathInfos.size() > 1) {
            throw new IllegalStateException("한 칸만 이동 가능합니다.");
        }
    }

    public void validateNoBlockingPiece(Position destination) {
        boolean hasBlockingPiece = pathInfos.stream()
                .filter(path -> !path.position().equals(destination))
                .anyMatch(PathInfo::hasPiece);

        if (hasBlockingPiece) {
            throw new IllegalArgumentException("이동 경로에 있는 다른 기물을 뛰어넘을 수 없습니다.");
        }
    }

    public void validateHasBlockingPiece(Position destination) {
        long blockingPieceCount = pathInfos.stream()
                .filter(pathInfo -> !pathInfo.position().equals(destination))
                .filter(PathInfo::hasPiece)
                .count();

        if (blockingPieceCount != 1) {
            throw new IllegalArgumentException("포는 반드시 하나의 기물만을 이동할 수 있습니다.");
        }
        if (pathInfos.stream().anyMatch(pathInfo -> pathInfo.isPieceType(PieceType.CANNON))) {
            throw new IllegalArgumentException("포는 포를 넘거나 잡을 수 없습니다.");
        }
    }

}
