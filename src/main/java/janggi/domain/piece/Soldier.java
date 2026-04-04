package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.SlidingMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Soldier extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.SOLDIER;
    private static final PieceAction RED_PIECE_ACTION;
    private static final PieceAction BLUE_PIECE_ACTION;

    static {
        final List<MoveRule> redMovementStrategies = List.of(
                new SlidingMoveRule(new Movement(1, Direction.LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.RIGHT)),
                new SlidingMoveRule(new Movement(1, Direction.DOWN)));
        final List<MoveRule> blueMovementStrategies = List.of(
                new SlidingMoveRule(new Movement(1, Direction.LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.RIGHT)),
                new SlidingMoveRule(new Movement(1, Direction.UP)));
        RED_PIECE_ACTION = new PieceAction(redMovementStrategies);
        BLUE_PIECE_ACTION = new PieceAction(blueMovementStrategies);
    }

    public Soldier(TeamType teamType) {
        super(teamType);
    }

    @Override
    protected PieceType getPieceType() {
        return PIECE_TYPE;
    }

    @Override
    protected PieceAction getPieceAction() {
        if (this.teamType == TeamType.RED) {
            return RED_PIECE_ACTION;
        }
        return BLUE_PIECE_ACTION;
    }
}