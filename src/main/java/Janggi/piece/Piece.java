package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;

public abstract class Piece {

    protected final Country country;

    public Piece(final Country country) {
        this.country = country;
    }

    public boolean isAbleToMove(final Position now, final Position destination, final Board board){
        if (board.existPieceByPosition(destination) && board.equalsTeamTypeByPosition(destination, country)) {
            return false;
        }
        return canMove(now, destination, board);
    };

    public abstract boolean canMove(final Position now, final Position destination, final Board board);

    public abstract boolean isCannon();

    public boolean equalsTeamType(final Country country) {
        return this.country == country;
    }

    public Country getTeamType() {
        return country;
    }
}
