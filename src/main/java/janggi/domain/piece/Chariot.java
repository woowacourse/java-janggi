package janggi.domain.piece;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.SlidingMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Chariot implements Piece {

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

    private final TeamType teamType;

    public Chariot(final TeamType teamType) {
        this.teamType = teamType;
    }

    @Override
    public boolean isSameTeamType(final TeamType teamType) {
        return this.teamType == teamType;
    }

    @Override
    public List<Position> calculateMovablePositions(final Position from, final BoardMediator boardMediator) {
        return PIECE_ACTION.calculateMovablePositions(from, teamType, boardMediator);
    }

    @Override
    public TeamType getTeamTypeForDTO() {
        return teamType;
    }

    @Override
    public PieceType getPieceTypeForDTO() {
        return PIECE_TYPE;
    }
}
