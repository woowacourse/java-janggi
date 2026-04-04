package janggi.domain.piece;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;

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
                new SlidingMoveRule(new Movement(MAXIMUM_ROW, Direction.UP)),
                new SlidingMoveRule(new Movement(MAXIMUM_ROW, Direction.DOWN)),
                new SlidingMoveRule(new Movement(MAXIMUM_COLUMN, Direction.RIGHT)),
                new SlidingMoveRule(new Movement(MAXIMUM_COLUMN, Direction.LEFT)));
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
