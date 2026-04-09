package janggi.domain.movestrategy;

import janggi.domain.movestrategy.rule.MoveRule;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;

public class CannonStrategy extends AbstractMoveStrategy {

    public CannonStrategy(List<MoveRule> moveRules) {
        super(moveRules);
    }

    @Override
    public boolean checkPathRule(List<Piece> pathPieces) {
        return pathPieces.size() == 1 &&
                !pathPieces.getFirst().isSameType(PieceType.CANNON);
    }

    @Override
    public boolean canCapture(Piece from, Piece to) {
        if (to == null) {
            return true;
        }
        if (from.isSameTeam(to)) {
            return false;
        }
        return !to.isSameType(PieceType.CANNON);
    }
}
