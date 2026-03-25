package domain;

import java.util.List;

abstract class Piece {

    protected final PieceProperty pieceProperty;
    protected final MoveStrategy moveStrategy;

    Piece(PieceProperty pieceProperty, MoveStrategy moveStrategy) {
        this.pieceProperty = pieceProperty;
        this.moveStrategy = moveStrategy;
    }

    protected abstract boolean isMoveAble(Position position);

    boolean isRootBlockedBy(List<Position> piecePositions) {
        return moveStrategy.isRootBlockedBy(piecePositions);
    }
}
