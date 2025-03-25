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

    public final Piece move(final Position destination, List<Piece> enemy, List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return makeMovedPiece(destination);
    }

    abstract public boolean ableToMove(final Position destination, List<Piece> enemy, List<Piece> allies);

    abstract protected Piece makeMovedPiece(Position position);

    public final PieceType getPieceType() {
        return pieceType;
    }

    public final boolean checkPieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public final Position getPosition() {
        return position;
    }
}
