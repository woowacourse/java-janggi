package janggi.domain.piece;

import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.CurveMoveStrategy;
import java.util.List;

public class Horse extends Piece {

    private static final Movement MOVEMENT = new Movement(List.of(1, 2));

    public Horse(final Team team) {
        super(team, PieceType.HORSE, new MoveRule(new CurveMoveStrategy(), new BasicBlockStrategy()));
    }

    @Override
    public void checkCanMove(final Placement placement, final Position departure, final Position destination) {
        validateMove(placement, departure, destination, MOVEMENT);
    }
}
