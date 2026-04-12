package domain.piece;

import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.state.Side;
import domain.strategy.MoveStrategy;

import java.util.List;

public abstract class Piece {

    private final PieceType type;
    private final Side side;
    private final MoveStrategy moveStrategy;

    public Piece(PieceType type, Side side, MoveStrategy moveStrategy) {
        this.type = type;
        this.side = side;
        this.moveStrategy = moveStrategy;
    }

    public Piece(PieceType type, Side side) {
        this(type, side, null);
    }

    public Side getSide() {
        return this.side;
    }

    public boolean isNeutral() {
        return side.isNeutral();
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public List<List<Direction>> getPotentialPaths(Position start) {
        return moveStrategy.calculatePotentialPaths(start);
    }

    public PieceType getType() {
        return type;
    }

    public abstract int getScore();
}
