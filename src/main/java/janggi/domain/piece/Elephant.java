package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.StepMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Elephant extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.ELEPHANT;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                StepMoveRule.elephantShape(Direction.UP, Direction.UP_LEFT),
                StepMoveRule.elephantShape(Direction.UP, Direction.UP_RIGHT),
                StepMoveRule.elephantShape(Direction.RIGHT, Direction.UP_RIGHT),
                StepMoveRule.elephantShape(Direction.RIGHT, Direction.DOWN_RIGHT),
                StepMoveRule.elephantShape(Direction.DOWN, Direction.DOWN_LEFT),
                StepMoveRule.elephantShape(Direction.DOWN, Direction.DOWN_RIGHT),
                StepMoveRule.elephantShape(Direction.LEFT, Direction.DOWN_LEFT),
                StepMoveRule.elephantShape(Direction.LEFT, Direction.UP_LEFT));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    public Elephant(TeamType teamType) {
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