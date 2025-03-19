package piece;

import strategy.MoveStrategy;

public class Piece {

    private final Position position;
    private final PieceRule pieceRule;

    public Piece(Position position, MoveStrategy moveStrategy, PieceType pieceType) {
        this.position = position;
        this.pieceRule = new PieceRule(moveStrategy,pieceType);
    }
}
