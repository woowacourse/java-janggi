package domain.place.piece;

import domain.board.BoardView;
import domain.place.Place;
import domain.place.moveStrategy.MoveStrategy;
import domain.position.Position;
import java.util.Optional;

public abstract class Piece implements Place {
    protected final Side side;
    protected final MoveStrategy moveStrategy;

    public Piece(Side side, MoveStrategy moveStrategy) {
        this.side = side;
        this.moveStrategy = moveStrategy;
    }

    public abstract PieceSymbol getSymbol();

    @Override
    public String getFormat() {
        return side.colorize(getSymbol().display());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    @Override
    public Optional<Side> getSide() {
        return Optional.ofNullable(side);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        return moveStrategy.canMove(board, from, to);
    }
}

