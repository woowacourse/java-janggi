package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Score;
import janggi.domain.piece.strategy.HorseStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Horse extends Piece {
    private static final PieceName HORSE_NAME = new PieceName("馬", "馬");
    private static final Score HORSE_SCORE = new Score(5);

    public Horse(Camp camp) {
        super(camp, HorseStrategy.getInstance(), HORSE_NAME, HORSE_SCORE);
    }

    @Override
    public boolean canPassRoute(Map<Position, Piece> piecesInPath) {
        return piecesInPath.isEmpty();
    }

    @Override
    public boolean canCatch(Piece piece) {
        return !this.isSameCamp(piece);
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
