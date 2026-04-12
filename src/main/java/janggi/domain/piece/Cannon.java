package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Palaces;
import janggi.domain.Score;
import janggi.domain.piece.strategy.LinearStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Cannon extends Piece {
    private static final PieceName CANNON_NAME = new PieceName("包", "砲");
    private static final Score CANNON_SCORE = new Score(7);

    public Cannon(Camp camp) {
        super(camp, new LinearStrategy(Palaces.of()), CANNON_NAME, CANNON_SCORE);
    }

    @Override
    public boolean canPassRoute(Map<Position, Piece> piecesInPath) {
        if (piecesInPath.size() != 1) {
            return false;
        }
        Piece pieceInPath = piecesInPath.values()
                .stream()
                .findFirst()
                .orElseThrow();
        return pieceInPath.canBeJumpedOver();
    }

    @Override
    public boolean canCatch(Piece piece) {
        if (!piece.canBeCaughtByCannon()) {
            return false;
        }
        return !isSameCamp(piece);
    }

    @Override
    public boolean canBeJumpedOver() {
        return false;
    }

    @Override
    public boolean canBeCaughtByCannon() {
        return false;
    }

    @Override
    public boolean isEssential() {
        return false;
    }
}
