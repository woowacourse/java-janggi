package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.SingleMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Guard extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.GUARD;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                new SingleMoveRule(new Movement(Direction.UP_LEFT)),
                new SingleMoveRule(new Movement(Direction.UP)),
                new SingleMoveRule(new Movement(Direction.UP_RIGHT)),
                new SingleMoveRule(new Movement(Direction.LEFT)),
                new SingleMoveRule(new Movement(Direction.RIGHT)),
                new SingleMoveRule(new Movement(Direction.DOWN_LEFT)),
                new SingleMoveRule(new Movement(Direction.DOWN)),
                new SingleMoveRule(new Movement(Direction.DOWN_RIGHT)));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    public Guard(TeamType teamType) {
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