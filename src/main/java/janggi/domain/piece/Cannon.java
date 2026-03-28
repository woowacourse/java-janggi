package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Cannon extends Piece{
    public Cannon(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
    }

    @Override
    public boolean moveRoute(Map<Position, Piece> abc) {
        return false;
    }

    @Override
    public boolean moveDestination(Position position, Piece piece) {
        return false;
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}
