package janggi.domain.piece;

import janggi.domain.movement.CannonMoveRule;
import janggi.domain.movement.CannonPalaceMoveRule;
import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Cannon extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.CANNON;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                new CannonMoveRule(Direction.UP),
                new CannonMoveRule(Direction.DOWN),
                new CannonMoveRule(Direction.RIGHT),
                new CannonMoveRule(Direction.LEFT),

                new CannonPalaceMoveRule(Direction.UP_LEFT),
                new CannonPalaceMoveRule(Direction.UP_RIGHT),
                new CannonPalaceMoveRule(Direction.DOWN_LEFT),
                new CannonPalaceMoveRule(Direction.DOWN_RIGHT));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    public Cannon(TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean isCannon() {
        return true;
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
