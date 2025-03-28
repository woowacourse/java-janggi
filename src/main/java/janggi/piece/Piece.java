package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.JanggiPosition;

public abstract class Piece {

    protected final Country country;

    public Piece(final Country country) {
        this.country = country;
    }

    public boolean isAbleToMove(final JanggiPosition now, final JanggiPosition destination, final VisibleBoard visibleBoard){
        if (visibleBoard.existPieceByPosition(destination) && visibleBoard.equalsTeamTypeByPosition(destination, country)) {
            return false;
        }
        return canMove(now, destination, visibleBoard);
    };

    public abstract JanggiScore plusScore(final JanggiScore janggiScore);

    protected abstract boolean canMove(final JanggiPosition now, final JanggiPosition destination, final VisibleBoard visibleBoard);

    public boolean isCannon(){
        return false;
    };

    public boolean isGeneral(){
        return false;
    }

    public boolean equalsCountry(final Country country) {
        return this.country == country;
    }

    public Country getCountry() {
        return country;
    }
}
