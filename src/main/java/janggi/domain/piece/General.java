package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.SlidingMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class General extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.GENERAL;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                new SlidingMoveRule(new Movement(1, Direction.UP_LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.UP)),
                new SlidingMoveRule(new Movement(1, Direction.UP_RIGHT)),
                new SlidingMoveRule(new Movement(1, Direction.LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.RIGHT)),
                new SlidingMoveRule(new Movement(1, Direction.DOWN_LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.DOWN)),
                new SlidingMoveRule(new Movement(1, Direction.DOWN_RIGHT)));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    public General(TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

    @Override
    protected PieceType getPieceType() {
        return PIECE_TYPE;
    }

    @Override
    protected PieceAction getPieceAction() {
        return PIECE_ACTION;
    }
}