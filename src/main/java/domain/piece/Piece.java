package domain.piece;

import domain.coordinate.Direction;
import domain.Game;
import domain.coordinate.Position;
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

    public Direction forward() {
        return side.getForward();
    }

    public abstract List<Position> getPossibleMoves(Game game, Position start);

}
