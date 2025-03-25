package janggi.domain.piece;

import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.OneStepMoveStrategy;
import java.util.List;

public class General extends Piece {

    private static final Movement MOVEMENT = new Movement(List.of(0, 1));

    public General(final Team team) {
        super(team, PieceType.GENERAL, new MoveRule(new OneStepMoveStrategy(), new BasicBlockStrategy()));
    }

    @Override
    public void checkCanMove(final Placement placement, final Position departure, final Position destination) {
        validateMove(placement, departure, destination, MOVEMENT);
    }
}
