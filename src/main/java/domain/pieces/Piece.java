package domain.pieces;

import domain.BoardChecker;
import domain.Camp;
import domain.PieceType;
import domain.Position;
import java.util.Optional;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public static Optional<Position> north(Position position) {
        return Position.of(position.getCol(), position.getRow() - 1);
    }

    public static Optional<Position> south(Position position) {
        return Position.of(position.getCol(), position.getRow() + 1);
    }

    public static Optional<Position> west(Position position) {
        return Position.of(position.getCol() - 1, position.getRow());
    }

    public static Optional<Position> east(Position position) {
        return Position.of(position.getCol() + 1, position.getRow());
    }

    public static Optional<Position> northWest(Position position) {
        return Position.of(position.getCol() - 1, position.getRow() - 1);
    }

    public static Optional<Position> northEast(Position position) {
        return Position.of(position.getCol() + 1, position.getRow() - 1);
    }

    public static Optional<Position> southWest(Position position) {
        return Position.of(position.getCol() - 1, position.getRow() + 1);
    }

    public static Optional<Position> southEast(Position position) {
        return Position.of(position.getCol() + 1, position.getRow() + 1);
    }

    public boolean isSameCamp(Piece comparedPiece) {
        return this.camp == comparedPiece.camp;
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }

    public abstract boolean canMove(Position from, Position to, BoardChecker boardChecker);

    public abstract PieceType getPieceType();

    public Camp getCamp() {
        return this.camp;
    }
}
