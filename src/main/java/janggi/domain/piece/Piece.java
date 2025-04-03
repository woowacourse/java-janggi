package janggi.domain.piece;

import janggi.domain.board.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.move.MoveStrategy;
import java.util.Objects;

public abstract class Piece {

    private final PieceType pieceType;
    private final Dynasty dynasty;
    private final MoveStrategy moveStrategy;

    public Piece(PieceType pieceType, Dynasty dynasty, MoveStrategy moveStrategy) {
        this.pieceType = pieceType;
        this.dynasty = dynasty;
        this.moveStrategy = moveStrategy;
    }

    public final boolean canMove(JanggiBoard janggiBoard, Dynasty currentTurnDynasty, Point start, Point end) {
        if (!isSameDynasty(currentTurnDynasty)) {
            throw new IllegalArgumentException("자신의 나라 기물이 아닙니다.");
        }
        if (!isMovable(janggiBoard, start, end)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return true;
    }

    public final boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    private boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return moveStrategy.isMovable(janggiBoard, this, start, end);
    }

    public final boolean isEqualPieceType(PieceType piece) {
        return this.pieceType == piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return pieceType == piece.pieceType && dynasty == piece.dynasty && Objects.equals(moveStrategy,
                piece.moveStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, dynasty, moveStrategy);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Dynasty getDynasty() {
        return dynasty;
    }

    public int getScore() {
        return pieceType.getScore();
    }
}
