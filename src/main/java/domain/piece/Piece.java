package domain.piece;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.strategy.MoveStrategy;

public class Piece {
    private static final String CANNOT_MOVE_SAME_CAMP_ERROR_MESSAGE = "[ERROR] 목적지에 같은 진영의 기물이 존재하여 이동할 수 없습니다.";
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

    public void move(Position from, Position to, PathChecker pathChecker) {
        if (pathChecker.isSameCamp(from, to)) {
            throw new IllegalArgumentException(CANNOT_MOVE_SAME_CAMP_ERROR_MESSAGE);
        }

        strategy.move(from, to, pathChecker);
    }
}
