package model.board;

import model.move.Move;

public class PalaceRoute {
    private final Move move;

    public PalaceRoute(Move move) {
        this.move = move;
    }

    public boolean matches(Move other){
        if(move.equals(other)){
            return true;
        }
        return isReverse(other);
    }

    private boolean isReverse(Move other){
        return move.from().equals(other.to())
                && move.to().equals(other.from());
    }
}
