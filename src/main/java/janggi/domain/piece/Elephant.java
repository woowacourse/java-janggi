package janggi.domain.piece;

import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.ElephantMoveStrategy;

public class Elephant extends Piece {

    public Elephant(final Team team) {
        super(team, PieceType.ELEPHANT, new MoveRule(new ElephantMoveStrategy(), new BasicBlockStrategy()));
    }

    @Override
    public void checkCanMove(final Placement placement, final Position departure, final Position destination) {
        validateMove(placement, departure, destination);
    }
}
