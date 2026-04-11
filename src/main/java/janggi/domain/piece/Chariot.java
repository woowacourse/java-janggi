package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.PalaceSlidingMoveRule;
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
                new SlidingMoveRule(new Movement(Direction.LEFT)),

                new PalaceSlidingMoveRule(new Movement(Direction.UP_LEFT)),
                new PalaceSlidingMoveRule(new Movement(Direction.UP_RIGHT)),
                new PalaceSlidingMoveRule(new Movement(Direction.DOWN_LEFT)),
                new PalaceSlidingMoveRule(new Movement(Direction.DOWN_RIGHT)));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    public Chariot(TeamType teamType) {
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
