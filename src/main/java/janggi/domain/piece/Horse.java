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

    static {
        final List<Rule> rules = List.of(
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.valueOf(0, 1)),
                new Movement(1, Direction.valueOf(-1, 1)))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.valueOf(0, 1)),
                new Movement(1, Direction.valueOf(1, 1)))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.valueOf(-1, 0)),
                new Movement(1, Direction.valueOf(-1, 1)))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.valueOf(-1, 0)),
                new Movement(1, Direction.valueOf(-1, -1)))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.valueOf(0, -1)),
                new Movement(1, Direction.valueOf(-1, -1)))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.valueOf(0, -1)),
                new Movement(1, Direction.valueOf(1, -1)))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.valueOf(1, 0)),
                new Movement(1, Direction.valueOf(1, -1)))),
            new RuleWithNoTraces(List.of(
                new Movement(1, Direction.valueOf(1, 0)),
                new Movement(1, Direction.valueOf(1, 1)))
            ));

        PIECE_ACTION = new PieceAction(rules);
    }

    private final TeamType teamType;

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
    public boolean isOnSameTeamAs(final Piece other) {
        return this.teamType == other.getTeamType();
    }

    @Override
    public List<Position> calculateMovablePositions(Position from, BoardMediator boardMediator) {
        return PIECE_ACTION.calculateMovablePositions(from, boardMediator);
    }

    @Override
    public boolean canKill(final Piece target) {
        return !UNCATCHABLE_PIECE_TYPES.contains(target.getPieceType()) && !target.isOnSameTeamAs(
            this);
    }
}
