package domain;

import domain.piece.category.PieceCategory;
import domain.spatial.Vector;
import java.util.List;

public class MoveInfos {

    private final List<MoveInfo> moveInfos;

    public MoveInfos(final List<MoveInfo> moveInfos) {
        validateMoveInfos(moveInfos);
        this.moveInfos = moveInfos;
    }

    public boolean isDiagonalPath() {
        MoveInfo start = moveInfos.getFirst();
        MoveInfo next = moveInfos.get(1);
        Vector direction = start.calculateDirection(next);

        return direction.isDiagonal();
    }

    public boolean isLastPathWithinPalace() {
        MoveInfo last = moveInfos.getLast();
        return last.isInsidePalace();
    }

    public int countPiecesInIntermediatePath() {
        List<MoveInfo> intermediatePaths = moveInfos.subList(1, moveInfos.size() - 1);
        return (int) intermediatePaths.stream()
                .filter(MoveInfo::hasPieceInPath)
                .count();
    }

    public boolean isSameAsTargetPiece(final PieceCategory start) {
        MoveInfo target = moveInfos.getLast();
        return target.isSamePieceCategory(start);
    }

    public boolean hasSamePieceCategoryInPath(final PieceCategory category) {
        List<MoveInfo> intermediatePaths = moveInfos.subList(1, moveInfos.size() - 1);
        return intermediatePaths.stream()
                .anyMatch(moveInfo -> moveInfo.isSamePieceCategory(category));
    }

    private void validateMoveInfos(final List<MoveInfo> moveInfos) {
        if (moveInfos.isEmpty() || moveInfos.size() < 2) {
            throw new IllegalArgumentException("이동할 수 없는 좌표입니다. 다시 확인해주세요.");
        }
    }
}
