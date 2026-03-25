package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.strategy.PoMoveStrategy;

import java.util.ArrayList;
import java.util.List;

public class Po extends Started {
    public Po(Side side) {
        super(new PoMoveStrategy(), side);
    }

    @Override
    public List<Position> calculatePath(Position start, Position end){
        return new ArrayList<>();
    }

    @Override
    public boolean isMovable(List<Position> path, BoardInterface boardInterface) {
        return false;
    }

    @Override
    public boolean isPo(){
        return true;
    }
}
