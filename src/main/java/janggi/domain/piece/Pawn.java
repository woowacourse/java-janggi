package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.strategy.DefaultMoveStrategy;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Started {
    public Pawn(Side side) {
        super(new DefaultMoveStrategy(), side);
    }

    @Override
    public List<Position> calculatePath(Position start, Position end){
        return new ArrayList<>();
    }

    @Override
    public boolean isMovable(List<Position> path, BoardInterface boardInterface) {
        return false;
    }
}
