package model.board;

import model.move.Move;
import model.position.Position;

public class PalaceDiagonal {
    private final Position start;
    private final Position end;

    public PalaceDiagonal(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public boolean matches(Move move){
        if(isSameDirection(move)){
            return true;
        }
        return isReverseDirection(move);
    }

    private boolean isSameDirection(Move move){
        return start.equals(move.from()) && end.equals(move.to());
    }

    private boolean isReverseDirection(Move move){
        return end.equals(move.from()) && start.equals(move.to());
    }
}
