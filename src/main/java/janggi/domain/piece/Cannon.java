package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.CannonMoveRule;
import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Cannon implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.CANNON;
    private static final PieceAction PIECE_ACTION;

    static {
        final List<MoveRule> movementStrategies = List.of(
                new CannonMoveRule(Direction.UP),
                new CannonMoveRule(Direction.DOWN),
                new CannonMoveRule(Direction.RIGHT),
                new CannonMoveRule(Direction.LEFT));
        PIECE_ACTION = new PieceAction(movementStrategies);
    }

    private final TeamType teamType;

    public Cannon(final TeamType teamType) {
        this.teamType = teamType;
    }

    @Override
    public PieceType getPieceType() {
        return PIECE_TYPE;
    }

    @Override
    public TeamType getTeamType() {
        return teamType;
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
    public boolean isCannon() {
        return true;
    }
}
