package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.SlidingMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Chariot extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.CHARIOT;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                new SlidingMoveRule(new Movement(Direction.UP)),
                new SlidingMoveRule(new Movement(Direction.DOWN)),
                new SlidingMoveRule(new Movement(Direction.RIGHT)),
                new SlidingMoveRule(new Movement(Direction.LEFT)));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    public Chariot(TeamType teamType) {
        super(teamType);
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
