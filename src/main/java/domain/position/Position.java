package domain.position;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.Objects;

public class Position {

    private final Point point;
    private final Piece piece;

    public Position(final Point point, final Piece piece) {
        this.point = point;
        this.piece = piece;
    }

    public boolean isSame(final Point other) {
        return this.point.equals(other);
    }

    public boolean isSamePiece(final Piece other) {
        return this.piece.equals(other);
    }

    public boolean isSamePieceType(final Position other) {
        return this.piece.type() == other.piece.type();
    }

    public boolean isSamePieceType(final PieceType otherPieceType) {
        return this.piece.type() == otherPieceType;
    }

    public boolean isGreenTeam() {
        return piece.isGreenTeam();
    }

    public boolean isMovableTo(final Point other) {
        return piece.isMovable(point.calculateDistance(other));
    }

    public List<Point> calculatePossiblePoint(final Point toPoint) {
        return piece.calculatePossiblePoint(this.point, toPoint);
    }

    public Position getNextPosition(final Point toPoint) {
        return new Position(toPoint, piece);
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return Objects.equals(point, position.point);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(point);
    }

    public boolean canPassOverPiece(
            final Position middlePosition,
            final Position toPosition,
            final PieceType pieceType
    ) {
        if (!isSamePieceType(pieceType)) {
            return false;
        }
        if (isSamePieceType(middlePosition)) {
            return false;
        }
        return !isSamePieceType(toPosition);
    }

    public boolean canPassOverPiece(final Position middlePosition, final PieceType pieceType) {
        if (!isSamePieceType(pieceType)) {
            return false;
        }
        return !isSamePieceType(middlePosition);
    }
}
