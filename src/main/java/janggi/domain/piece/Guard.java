package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.PalaceMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Guard extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.GUARD;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                new PalaceMoveRule(Direction.UP_LEFT),
                new PalaceMoveRule(Direction.UP),
                new PalaceMoveRule(Direction.UP_RIGHT),
                new PalaceMoveRule(Direction.LEFT),
                new PalaceMoveRule(Direction.RIGHT),
                new PalaceMoveRule(Direction.DOWN_LEFT),
                new PalaceMoveRule(Direction.DOWN),
                new PalaceMoveRule(Direction.DOWN_RIGHT));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    public Guard(TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean isPalacePiece() {
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