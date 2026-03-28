package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.Movement;
import janggi.domain.movement.Rule;
import janggi.domain.movement.RuleWithNoTraces;
import janggi.domain.team.TeamType;
import java.util.List;

public class Horse implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.HORSE;
    private static final PieceAction PIECE_ACTION;
    private static final List<PieceType> UNCATCHABLE_PIECE_TYPES = List.of();

    private final TeamType teamType;

    static {
        final List<Rule> rules = List.of(
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.RIGHT),
                new Movement(1, Direction.UP_RIGHT))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.RIGHT),
                new Movement(1, Direction.DOWN_RIGHT))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.UP),
                new Movement(1, Direction.UP_RIGHT))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.UP),
                new Movement(1, Direction.UP_LEFT))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.LEFT),
                new Movement(1, Direction.UP_LEFT))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.LEFT),
                new Movement(1, Direction.DOWN_LEFT))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.DOWN),
                new Movement(1, Direction.DOWN_LEFT))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.DOWN),
                new Movement(1, Direction.DOWN_RIGHT)))
            );

        PIECE_ACTION = new PieceAction(rules);
    }

    public Horse(final TeamType teamType) {
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
    public List<Position> calculateMovablePositions(Position from, BoardMediator boardMediator) {
        return PIECE_ACTION.calculateMovablePositions(from,boardMediator);
    }

    @Override
    public boolean canKill(final Piece target) {
        return !UNCATCHABLE_PIECE_TYPES.contains(target.getPieceType()) && !target.belongsToTeam(
            teamType);
    }
}
