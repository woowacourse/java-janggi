package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Score;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Elephant extends Piece {
    private static final PieceName ELEPHANT_NAME = new PieceName("象", "象");
    private static final Score ELEPHANT_SCORE = new Score(3);

    public Elephant(Camp camp) {
        super(camp, ElephantStrategy.getInstance(), ELEPHANT_NAME, ELEPHANT_SCORE);
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
