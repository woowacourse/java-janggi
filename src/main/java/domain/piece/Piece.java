package domain.piece;

import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.List;
import java.util.Objects;

public abstract class Piece {

    private final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return this.side;
    }

    public boolean isChu() {
        return side.isChu();
    }

    public boolean isHan() {
        return side.isHan();
    }

    public boolean isNeutral() {
        return side.isNeutral();
    }

    public boolean isFriendly(Side side) {
        return this.side == side;
    }

    public int forward() {
        return side.getForward();
    }

    public abstract List<Position> getPossibleMoves(Board board, Position start);

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return side == piece.side;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(side);
    }
}
