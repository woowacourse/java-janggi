package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Score;
import janggi.domain.piece.strategy.PalaceStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Advisor extends Piece {
    private static final PieceName ADVISOR_NAME = new PieceName("士", "仕");
    private static final Score ADVISOR_SCORE = new Score(3);

    public Advisor(Camp camp) {
        super(camp, PalaceStrategy.getInstance(), ADVISOR_NAME, ADVISOR_SCORE);
    }

    @Override
    public boolean canPassRoute(Map<Position, Piece> piecesInPath) {
        return true;
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
