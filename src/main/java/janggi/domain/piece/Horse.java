package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.StepMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Horse extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.HORSE;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                StepMoveRule.horseShape(Direction.UP, Direction.UP_LEFT),
                StepMoveRule.horseShape(Direction.UP, Direction.UP_RIGHT),
                StepMoveRule.horseShape(Direction.RIGHT, Direction.UP_RIGHT),
                StepMoveRule.horseShape(Direction.RIGHT, Direction.DOWN_RIGHT),
                StepMoveRule.horseShape(Direction.DOWN, Direction.DOWN_LEFT),
                StepMoveRule.horseShape(Direction.DOWN, Direction.DOWN_RIGHT),
                StepMoveRule.horseShape(Direction.LEFT, Direction.DOWN_LEFT),
                StepMoveRule.horseShape(Direction.LEFT, Direction.UP_LEFT));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    public Horse(TeamType teamType) {
        super(teamType);
    }

    @Override
    public int score() {
        return pieceType().score();
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