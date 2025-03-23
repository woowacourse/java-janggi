package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import java.util.Objects;

public class BoardPiece {

    private final Piece piece;
    private final Dynasty dynasty;

    public BoardPiece(Piece piece, Dynasty dynasty) {
        this.piece = piece;
        this.dynasty = dynasty;
    }

    public void move(JanggiBoard janggiBoard, Position start, Position end) {
        validateExistSameDynastyPiece(janggiBoard, end);
        if (!piece.isMovable(janggiBoard, dynasty, start, end)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    public boolean isEqualPiece(Piece piece) {
        return this.piece.equals(piece);
    }

    private void validateExistSameDynastyPiece(JanggiBoard janggiBoard, Position endPoint) {
        janggiBoard.findPointPiece(endPoint)
                .ifPresent((pointPiece -> {
                    if (pointPiece.isSameDynasty(dynasty)) {
                        throw new IllegalArgumentException("이미 놓여져 있는 기물이 존재합니다.");
                    }
                }));
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
