package domain.place.piece;

import domain.board.BoardView;
import domain.place.moveStrategy.MoveStrategy;
import domain.position.Position;

public abstract class Piece {
    protected final Side side;
    protected final MoveStrategy moveStrategy;

    public Piece(Side side, MoveStrategy moveStrategy) {
        this.side = side;
        this.moveStrategy = moveStrategy;
    }

    public abstract PieceSymbol getSymbol();

    public abstract double getScore();

    public String getFormat() {
        return side.colorize(getSymbol().display());
    }

    public boolean isSameSide(Piece piece) {
        return this.side == piece.side;
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public boolean isCannon() {
        return false;
    }

    public boolean isGeneral() {
        return false;
    }

    public boolean canMove(BoardView board, Position from, Position to) {
        return moveStrategy.canMove(board, from, to);
    }
}
