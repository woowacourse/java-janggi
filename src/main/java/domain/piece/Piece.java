package domain.piece;

import domain.board.BoardBounds;
import domain.coordinate.Direction;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.Side;

import java.util.List;
import java.util.Map;

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

    public boolean isFriendly(Side side) {
        return this.side == side;
    }

    public Direction forward() {
        return side.getForward();
    }

    public abstract PieceType getType();

    public abstract Piece withSide(Side side);

    public abstract boolean isEmpty();

    public abstract List<Path> getPaths(Position start, BoardBounds bounds);

    public abstract List<Position> getPossiblePositions(Map<Position, Piece> pathPieces, List<Path> paths);
}