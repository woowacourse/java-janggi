package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.SlidingMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class General implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.GENERAL;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                new SlidingMoveRule(new Movement(1, Direction.UP_LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.UP)),
                new SlidingMoveRule(new Movement(1, Direction.UP_RIGHT)),
                new SlidingMoveRule(new Movement(1, Direction.LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.RIGHT)),
                new SlidingMoveRule(new Movement(1, Direction.DOWN_LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.DOWN)),
                new SlidingMoveRule(new Movement(1, Direction.DOWN_RIGHT)));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    private final TeamType teamType;

    public General(final TeamType teamType) {
        this.teamType = teamType;
    }

    @Override
    public boolean isSameTeamType(final TeamType teamType) {
        return this.teamType == teamType;
    }

    @Override
    public List<Position> calculateMovablePositions(Position from, BoardMediator boardMediator) {
        return PIECE_ACTION.calculateMovablePositions(from, teamType, boardMediator);
    }

    @Override
    public boolean isGeneral() {
        return true;
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
