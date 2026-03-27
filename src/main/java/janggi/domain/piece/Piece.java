package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.List;
import java.util.Map;

public abstract class Piece {
    private final Camp camp;
    private final MoveStrategy moveStrategy;

    Piece(Camp camp, MoveStrategy moveStrategy) {
        this.camp = camp;
        this.moveStrategy = moveStrategy;
    }

    public List<Path> findMovablePaths(Position current) {
        return moveStrategy.findMovablePaths(current);
    }

    public boolean isSameCamp(Piece piece){
        return this.camp.isSameCamp(piece.camp);
    }

    abstract public boolean moveRoute(Map<Position, Piece> abc);
    abstract public boolean moveDestination(Position position, Piece piece);
}
