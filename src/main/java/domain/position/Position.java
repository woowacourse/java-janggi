package domain.position;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;

public final class Position {

    private final Point point;
    private final Piece piece;

    private Position(final Point point, final Piece piece) {
        this.point = point;
        this.piece = piece;
    }

    public static Position newInstance(final Point point, final Piece piece) {
        return new Position(point, piece);
    }

    public boolean isSame(final Point other) {
        return this.point.equals(other);
    }

    public boolean isSamePieceType(final PieceType otherPieceType) {
        return this.piece.type() == otherPieceType;
    }

    private boolean isDifferentPieceType(final Position other) {
        return this.piece.type() != other.piece.type();
    }

    public boolean isGreenTeam() {
        return piece.isGreenTeam();
    }

    public boolean isMovableTo(final Point other) {
        return piece.isMovable(point, other);
    }

    public List<Point> calculatePossiblePoint(final Point toPoint) {
        return piece.calculatePossiblePoint(this.point, toPoint);
    }

    public boolean canPassOverPiece(
            final Position middlePosition,
            final Position toPosition,
            final PieceType pieceType
    ) {
        return canPassOverPieceWith(middlePosition, pieceType) && isDifferentPieceType(toPosition);
    }

    public boolean canPassOverPiece(final Position middlePosition, final PieceType pieceType) {
        return canPassOverPieceWith(middlePosition, pieceType);
    }

    private boolean canPassOverPieceWith(final Position middlePosition, final PieceType pieceType) {
        return isSamePieceType(pieceType) && isDifferentPieceType(middlePosition);
    }

    public Position getNextPosition(final Point toPoint) {
        return new Position(toPoint, piece);
    }

    public Piece getPiece() {
        return piece;
    }
}
