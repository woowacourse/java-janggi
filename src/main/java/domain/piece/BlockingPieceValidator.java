package domain.piece;

import domain.board.Position;
import domain.path.PathInfo;

import java.util.List;

public class BlockingPieceValidator {
    public static void validateOnlyOneStep(List<PathInfo> pathInfos){
        if (pathInfos.size() > 1) {
            throw new IllegalStateException("한 칸만 이동 가능합니다.");
        }
    }
    public static void validateNoBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        boolean hasBlockingPiece = pathInfos.stream()
                .filter(path -> !path.position().equals(destination))
                .anyMatch(PathInfo::hasPiece);

        if (hasBlockingPiece) {
            throw new IllegalArgumentException("이동 경로에 있는 다른 기물을 뛰어넘을 수 없습니다.");
        }
    }

    public static void validateHasBlockingPiece(List<PathInfo> pathInfos) {
        if (pathInfos.size() != 2) {
            throw new IllegalArgumentException("포는 반드시 하나의 기물만을 이동할 수 있습니다.");
        }
        if (pathInfos.stream().anyMatch(pathInfo -> pathInfo.isPieceType(PieceType.CANNON))) {
            throw new IllegalArgumentException("포는 포를 넘거나 잡을 수 없습니다.");
        }
    }

}
