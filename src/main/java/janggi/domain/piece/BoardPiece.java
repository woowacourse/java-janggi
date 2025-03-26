package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Objects;

public class BoardPiece {

    private final Piece piece;
    private final Dynasty dynasty;

    public BoardPiece(Piece piece, Dynasty dynasty) {
        this.piece = piece;
        this.dynasty = dynasty;
    }

    public boolean canMove(JanggiBoard janggiBoard, Dynasty currentTurnDynasty, Point start, Point end) {
        if (!isSameDynasty(currentTurnDynasty)) {
            throw new IllegalArgumentException("자신의 나라 기물이 아닙니다.");
        }
        if (!piece.isMovable(janggiBoard, start, end)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return true;
    }

    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    public boolean isEqualPieceType(Piece piece) {
        return this.piece.getClass().equals(piece.getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoardPiece that = (BoardPiece) o;
        return Objects.equals(piece, that.piece)
                && dynasty == that.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, dynasty);
    }

    public Piece getPiece() {
        return piece;
    }

    public Dynasty getDynasty() {
        return dynasty;
    }
}
