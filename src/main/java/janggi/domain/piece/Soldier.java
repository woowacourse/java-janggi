package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.ChoSoldierStrategy;
import janggi.domain.piece.strategy.HanSoldierStrategy;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Soldier extends Piece {
    private static final ChoSoldierStrategy CHO_SOLDIER_STRATEGY = new ChoSoldierStrategy();
    private static final HanSoldierStrategy HAN_SOLDIER_STRATEGY = new HanSoldierStrategy();

    public Soldier(Camp camp) {
        super(camp, createStrategy(camp));
    }

    private static MoveStrategy createStrategy(Camp camp) {
        if (camp.isCho()) {
            return CHO_SOLDIER_STRATEGY;
        }
        return HAN_SOLDIER_STRATEGY;
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
    public String displayHanja() {
        return displayName("卒", "兵");
    }
}
