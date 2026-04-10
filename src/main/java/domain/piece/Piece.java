package domain.piece;

import domain.board.BoardReader;
import domain.Camp;
import domain.PieceType;
import domain.position.Position;

public abstract class Piece {

    private final PieceType pieceType;
    private final Camp camp;

    public Piece(Camp camp, PieceType pieceType) {
        this.camp = camp;
        this.pieceType = pieceType;
    }

    public boolean isSameCamp(Piece comparedPiece) {
        return this.camp == comparedPiece.camp;
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }

    public boolean isDifferentPieceType(Piece piece) {
        return this.getPieceType()!= piece.getPieceType();
    }

    public abstract boolean canMove(Position from, Position to, BoardReader boardReader);

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public Camp getCamp() {
        return this.camp;
    }

    public int getScore() {
        return pieceType.getScore();
    }
}
