package domain;

import domain.piece.category.PieceCategory;
import java.util.List;

public class MoveInfos {

    private final List<MoveInfo> moveInfos;

    public MoveInfos(final List<MoveInfo> moveInfos) {
        this.moveInfos = moveInfos;
    }

    public int countPiecesInIntermediatePath() {
        List<MoveInfo> intermediatePaths = moveInfos.subList(0, moveInfos.size() - 1);
        return (int) intermediatePaths.stream()
                .filter(MoveInfo::hasPieceInPath)
                .count();
    }

    public boolean isSameAsTargetPiece(final PieceCategory start) {
        MoveInfo target = moveInfos.getLast();
        return target.isSamePieceCategory(start);
    }

    public boolean hasSamePieceCategoryInPath(final PieceCategory category) {
        return moveInfos.stream()
                .anyMatch(moveInfo -> moveInfo.isSamePieceCategory(category));
    }
}
