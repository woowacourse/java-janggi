package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Soldier extends Piece{
    public Soldier(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
    }

    @Override
    public boolean moveRoute(Map<Position, Piece> pieceInPath) {
        return true;
    }

    @Override
    public boolean moveDestination(Position position, Piece piece) {
        return !isSameCamp(piece);
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
