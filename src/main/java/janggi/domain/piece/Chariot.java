package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Score;
import janggi.domain.piece.strategy.LinearStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Chariot extends Piece {
    private static final LinearStrategy LINEAR_STRATEGY = new LinearStrategy();
    private static final PieceName CHARIOT_NAME = new PieceName("車", "車");
    private static final Score CHARIOT_SCORE = new Score(13);

    public Chariot(Camp camp) {
        super(camp, LINEAR_STRATEGY, CHARIOT_NAME, CHARIOT_SCORE);
    }

    @Override
    public boolean canPassRoute(Map<Position, Piece> piecesInPath) {
        return piecesInPath.isEmpty();
    }

    @Override
    public boolean canCatch(Piece piece) {
        return !isSameCamp(piece);
    }

    @Override
    public boolean canBeJumpedOver() {
        return true;
    }

    @Override
    public boolean canBeCaughtByCannon() {
        return true;
    }
}
