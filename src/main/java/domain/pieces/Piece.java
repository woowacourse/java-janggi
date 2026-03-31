package domain.pieces;

import domain.Camp;
import domain.ExistBoard;
import domain.PieceType;
import domain.Position;

public abstract class Piece {

    private final PieceType pieceType = PieceType.NONE;
    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public boolean isSameCamp(Piece comparedPiece) {
        return this.camp == comparedPiece.camp;
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }

    public boolean isDifferentPieceType(Piece piece) {
        return this.getClass() != piece.getClass();
    }

    Position up(Position position) {
        int x = position.getX();
        int y = position.getY();
        return new Position(x, --y);
    }

    Position down(Position position) {
        int x = position.getX();
        int y = position.getY();
        return new Position(x, ++y);
    }

    Position left(Position position) {
        int x = position.getX();
        int y = position.getY();
        return new Position(--x, y);
    }

    Position right(Position position) {
        int x = position.getX();
        int y = position.getY();
        return new Position(++x, y);
    }

    Position leftUpDiagonal(Position position) {
        int x = position.getX();
        int y = position.getY();
        return new Position(--x, --y);
    }

    Position rightUpDiagonal(Position position) {
        int x = position.getX();
        int y = position.getY();
        return new Position(++x, --y);
    }

    Position leftDownDiagonal(Position position) {
        int x = position.getX();
        int y = position.getY();
        return new Position(--x, ++y);
    }

    Position rightDownDiagonal(Position position) {
        int x = position.getX();
        int y = position.getY();
        return new Position(++x, ++y);
    }

    public abstract boolean canMove(Position from, Position to, ExistBoard existBoard);

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public Camp getCamp() {
        return this.camp;
    }
}
