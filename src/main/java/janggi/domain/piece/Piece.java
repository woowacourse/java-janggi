package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Paths;
import janggi.domain.Score;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public abstract class Piece {
    private final Camp camp;
    private final MoveStrategy moveStrategy;
    private final PieceName pieceName;
    private final Score score;

    Piece(Camp camp, MoveStrategy moveStrategy, PieceName pieceName, Score score) {
        this.camp = camp;
        this.moveStrategy = moveStrategy;
        this.pieceName = pieceName;
        this.score = score;
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

    public String pieceName() {
        return pieceName.of(camp);
    }

    public Camp pieceCamp() {
        return camp;
    }

    public Score score() {
        return score;
    }

    abstract public boolean canPassRoute(Map<Position, Piece> piecesInPath);

    abstract public boolean canCatch(Piece piece);

    abstract public boolean canBeJumpedOver();

    abstract public boolean canBeCaughtByCannon();

    abstract public boolean isEssential();
}
