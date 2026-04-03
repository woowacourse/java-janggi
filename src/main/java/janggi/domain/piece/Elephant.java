package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.StepMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Elephant implements Piece {

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

    private final TeamType teamType;

    public Elephant(final TeamType teamType) {
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
    public boolean belongsToTeam(final TeamType teamType) {
        return this.teamType == teamType;
    }

    @Override
    public List<Position> calculateMovablePositions(final Position from,
                                                    final BoardMediator boardMediator) {
        return PIECE_ACTION.calculateMovablePositions(from, teamType, boardMediator);
    }
}
