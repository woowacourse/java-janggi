package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.position.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Elephant extends Piece {
    private static final ElephantStrategy ELEPHANT_STRATEGY = new ElephantStrategy();

    public Elephant(Camp camp) {
        super(camp, ELEPHANT_STRATEGY);
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
    public String displayHanja() {
        return displayName("象", "象");
    }
}
