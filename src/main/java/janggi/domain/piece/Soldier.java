package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Score;
import janggi.domain.piece.strategy.ChoSoldierStrategy;
import janggi.domain.piece.strategy.HanSoldierStrategy;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Soldier extends Piece {
    private static final PieceName SOLDIER_NAME = new PieceName("卒", "兵");
    private static final Score SOLDIER_SCORE = new Score(2);

    public Soldier(Camp camp) {
        super(camp, createStrategy(camp), SOLDIER_NAME, SOLDIER_SCORE);
    }

    private static MoveStrategy createStrategy(Camp camp) {
        if (camp.isCho()) {
            return ChoSoldierStrategy.getInstance();
        }
        return HanSoldierStrategy.getInstance();
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
        return false;
    }
}
