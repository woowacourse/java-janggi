package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;
import java.util.Objects;

public class BoardPiece {

    private final Piece piece;
    private final Dynasty dynasty;
    private Point currentPoint;

    public BoardPiece(Point currentPoint, Piece piece, Dynasty dynasty) {
        this.currentPoint = currentPoint;
        this.piece = piece;
        this.dynasty = dynasty;
    }

    public void move(JanggiBoard janggiBoard, Point endPoint) {
        validateExistSameDynastyPiece(janggiBoard, endPoint);
        if (!piece.isMovable(janggiBoard, currentPoint, endPoint)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        currentPoint = currentPoint.copy(endPoint);
    }

    private void validateExistSameDynastyPiece(JanggiBoard janggiBoard, Point endPoint) {
        janggiBoard.findPointPiece(endPoint)
                .ifPresent((pointPiece -> {
                    if (pointPiece.isSameDynasty(dynasty)) {
                        throw new IllegalArgumentException("이미 놓여져 있는 기물이 존재합니다.");
                    }
                }));
    }

    private boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    public boolean isSamePosition(Point point) {
        return this.currentPoint.isSamePosition(point);
    }

    public boolean isEqualPiece(Piece piece) {
        return this.piece.equals(piece);
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
        return Objects.equals(currentPoint, that.currentPoint) && Objects.equals(piece, that.piece)
                && dynasty == that.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPoint, piece, dynasty);
    }

    public Piece getPiece() {
        return piece;
    }

    public Dynasty getDynasty() {
        return dynasty;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }
}
