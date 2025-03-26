package janggi.domain.piece;

import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.HorseMoveStrategy;

public class Horse extends Piece {

    public Horse(final Team team) {
        super(team, PieceType.HORSE, new MoveRule(new HorseMoveStrategy(), new BasicBlockStrategy()));
    }

    @Override
    public void checkCanMove(final Placement placement, final Position departure, final Position destination) {
        validateMove(placement, departure, destination);
    }
}
