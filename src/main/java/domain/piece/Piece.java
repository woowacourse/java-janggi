package domain.piece;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.strategy.MoveStrategy;

public class Piece {
    private final Camp camp;
    private final PieceType pieceType;
    private final MoveStrategy strategy;

    public Piece(Camp camp, PieceType pieceType, MoveStrategy strategy) {
        this.camp = camp;
        this.pieceType = pieceType;
        this.strategy = strategy;
    }

    public Camp camp() {
        return this.camp;
    }

    public PieceType type() {
        return this.pieceType;
    }

    public void move(Position from, Position to, PathChecker checker) {
        strategy.move(from, to, checker);
    }
}
