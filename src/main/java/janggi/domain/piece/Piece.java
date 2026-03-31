package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Paths;
import janggi.domain.position.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public abstract class Piece {
    private final Camp camp;
    private final MoveStrategy moveStrategy;

    Piece(Camp camp, MoveStrategy moveStrategy) {
        this.camp = camp;
        this.moveStrategy = moveStrategy;
    }

    public Paths findMovablePaths(Position current) {
        return moveStrategy.findMovablePaths(current);
    }

    public boolean isSameCamp(Piece piece) {
        return this.camp.isSameCamp(piece.camp);
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp.isSameCamp(camp);
    }

    String displayName(String choName, String hanName) {
        if (camp.isCho()) {
            return choName;
        }
        return hanName;
    }

    abstract public boolean canPassRoute(Map<Position, Piece> piecesInPath);

    abstract public boolean canCatch(Piece piece);

    abstract public boolean canBeJumpedOver();

    abstract public boolean canBeCaughtByCannon();

    abstract public String displayHanja();
}
