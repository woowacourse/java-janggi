package janggi.domain.piece;

import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.PalaceMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class General extends AbstractPiece {
    private static final PieceType PIECE_TYPE = PieceType.GENERAL;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                new PalaceMoveRule(new Movement(Direction.UP_LEFT)),
                new PalaceMoveRule(new Movement(Direction.UP)),
                new PalaceMoveRule(new Movement(Direction.UP_RIGHT)),
                new PalaceMoveRule(new Movement(Direction.LEFT)),
                new PalaceMoveRule(new Movement(Direction.RIGHT)),
                new PalaceMoveRule(new Movement(Direction.DOWN_LEFT)),
                new PalaceMoveRule(new Movement(Direction.DOWN)),
                new PalaceMoveRule(new Movement(Direction.DOWN_RIGHT)));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    public General(TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean isGeneral() {
        return true;
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