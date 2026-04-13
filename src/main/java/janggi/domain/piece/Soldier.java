package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.PalaceMoveRule;
import janggi.domain.movement.SingleMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Soldier extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.SOLDIER;
    private static final PieceAction RED_PIECE_ACTION;
    private static final PieceAction BLUE_PIECE_ACTION;

    static {
        final List<MoveRule> redMovementStrategies = List.of(
                new SingleMoveRule(new Movement(Direction.LEFT)),
                new SingleMoveRule(new Movement(Direction.RIGHT)),
                new SingleMoveRule(new Movement(Direction.DOWN)),
                new PalaceMoveRule(Direction.DOWN_LEFT),
                new PalaceMoveRule(Direction.DOWN_RIGHT));
        final List<MoveRule> blueMovementStrategies = List.of(
                new SingleMoveRule(new Movement(Direction.LEFT)),
                new SingleMoveRule(new Movement(Direction.RIGHT)),
                new SingleMoveRule(new Movement(Direction.UP)),
                new PalaceMoveRule(Direction.UP_LEFT),
                new PalaceMoveRule(Direction.UP_RIGHT));
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