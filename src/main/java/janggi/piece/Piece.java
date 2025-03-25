package janggi.piece;

import janggi.board.VisibleBoard;
import janggi.coordinate.Position;

public abstract class Piece {

    protected final Country country;

    public Piece(final Country country) {
        this.country = country;
    }

    public boolean isAbleToMove(final Position now, final Position destination, final VisibleBoard visibleBoard){
        if (visibleBoard.existPieceByPosition(destination) && visibleBoard.equalsTeamTypeByPosition(destination, country)) {
            return false;
        }
        return canMove(now, destination, visibleBoard);
    };

    protected abstract boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard);

    public abstract boolean isCannon();

    public boolean equalsTeamType(final Country country) {
        return this.country == country;
    }

    public Country getTeamType() {
        return country;
    }
}
