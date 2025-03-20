package janggi.piece;

import janggi.value.Position;
import java.util.List;

public abstract class Piece {

    private final PieceType pieceType;
    private final Position position;

    protected Piece(final PieceType pieceType, final Position position) {
        this.pieceType = pieceType;
        this.position = position;
    }

    abstract public Piece move(final Position destination, List<Piece> enemy, List<Piece> allies);

    abstract public boolean ableToMove(final Position destination, List<Piece> enemy, List<Piece> allies);

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean checkPieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public Position getPosition() {
        return position;
    }
}
