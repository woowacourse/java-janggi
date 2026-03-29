package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.StepMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Elephant implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.ELEPHANT;
    private static final PieceAction PIECE_ACTION;
    private static final List<PieceType> UNCATCHABLE_PIECE_TYPES = List.of();

    static {
        final List<MoveRule> movementStrategies = List.of(
                new StepMoveRule(List.of(
                        new Movement(1, Direction.RIGHT),
                        new Movement(1, Direction.UP_RIGHT),
                        new Movement(1, Direction.UP_RIGHT))),
                new StepMoveRule(List.of(
                        new Movement(1, Direction.RIGHT),
                        new Movement(1, Direction.DOWN_RIGHT),
                        new Movement(1, Direction.DOWN_RIGHT))),
                new StepMoveRule(List.of(
                        new Movement(1, Direction.UP),
                        new Movement(1, Direction.UP_RIGHT),
                        new Movement(1, Direction.UP_RIGHT))),
                new StepMoveRule(List.of(
                        new Movement(1, Direction.UP),
                        new Movement(1, Direction.UP_LEFT),
                        new Movement(1, Direction.UP_LEFT))),
                new StepMoveRule(List.of(
                        new Movement(1, Direction.LEFT),
                        new Movement(1, Direction.UP_LEFT),
                        new Movement(1, Direction.UP_LEFT))),
                new StepMoveRule(List.of(
                        new Movement(1, Direction.LEFT),
                        new Movement(1, Direction.DOWN_LEFT),
                        new Movement(1, Direction.DOWN_LEFT))),
                new StepMoveRule(List.of(
                        new Movement(1, Direction.DOWN),
                        new Movement(1, Direction.DOWN_LEFT),
                        new Movement(1, Direction.DOWN_LEFT))),
                new StepMoveRule(List.of(
                        new Movement(1, Direction.DOWN),
                        new Movement(1, Direction.DOWN_RIGHT),
                        new Movement(1, Direction.DOWN_RIGHT)))
        );

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
        return PIECE_ACTION.calculateMovablePositions(from, boardMediator);
    }

    @Override
    public boolean canKill(final Piece target) {
        return !UNCATCHABLE_PIECE_TYPES.contains(target.getPieceType()) && !target.belongsToTeam(
                teamType);
    }
}
