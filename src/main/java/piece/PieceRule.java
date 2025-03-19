package piece;

import strategy.MoveStrategy;

public class PieceRule {

    private final MoveStrategy moveStrategy;
    private final PieceType pieceType;

    public PieceRule(MoveStrategy moveStrategy, PieceType pieceType) {
        this.moveStrategy = moveStrategy;
        this.pieceType = pieceType;
    }
}
