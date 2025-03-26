package domain;

import domain.piece.category.PieceCategory;
import java.util.List;

public class MoveInfos {

    private final List<MoveInfo> moveInfos;

    public MoveInfos(final List<MoveInfo> moveInfos) {
        this.moveInfos = moveInfos;
    }

    public int countPiecesInPath() {
        return (int) moveInfos.stream()
                .filter(MoveInfo::hasPieceInPath)
                .count();
    }

    public boolean isSameAsTargetPiece(final PieceCategory startPiece) {
        MoveInfo lastMove = moveInfos.getFirst();
        return lastMove.isSamePieceCategory(startPiece);
    }
}
