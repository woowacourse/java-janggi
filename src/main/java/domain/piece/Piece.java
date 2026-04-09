package domain.piece;

import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.strategy.MoveStrategy;

public class Piece {
    private final Camp camp;
    private final PieceType pieceType;
    private final MoveStrategy strategy;

    public Piece(Camp camp, PieceType pieceType) {
        this.camp = camp;
        this.pieceType = pieceType;
        this.strategy = pieceType.createStrategy(camp);
    }

    public Camp camp() {
        return this.camp;
    }

    public PieceType type() {
        return this.pieceType;
    }

    public void validateMove(Position from, Position to, BoardChecker boardChecker) {
        if (boardChecker.isSameCamp(from, to)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 같은 진영의 기물이 존재하여 이동할 수 없습니다.");
        }

        strategy.validateMove(from, to, boardChecker);
    }

    public int score() {
        return pieceType.score();
    }
}
