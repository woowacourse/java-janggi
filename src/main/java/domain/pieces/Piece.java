package domain.pieces;

import domain.Camp;
import domain.ExistBoard;
import domain.PieceType;
import domain.Position;

public abstract class Piece {

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

    public abstract boolean canMove(Position from, Position to, ExistBoard existBoard);

    public abstract PieceType getPieceType();

    public Camp getCamp() {
        return this.camp;
    }
}
