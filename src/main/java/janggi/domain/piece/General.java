package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Score;
import janggi.domain.piece.strategy.ChoPalaceStrategy;
import janggi.domain.piece.strategy.HanPalaceStrategy;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class General extends Piece {
    private static final PieceName GENERAL_NAME = new PieceName("楚", "漢");
    private static final Score GENERAL_SCORE = new Score(0);

    public General(Camp camp) {
        super(camp, createStrategy(camp), GENERAL_NAME, GENERAL_SCORE);
    }

    private static MoveStrategy createStrategy(Camp camp) {
        if (camp.isCho()) {
            return ChoPalaceStrategy.getInstance();
        }
        return HanPalaceStrategy.getInstance();
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

    @Override
    public boolean isEssential() {
        return true;
    }
}
