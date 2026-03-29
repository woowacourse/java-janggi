package janggi.domain.piece;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.Movement;
import janggi.domain.movement.Rule;
import janggi.domain.movement.RuleWithTraces;
import janggi.domain.team.TeamType;
import java.util.List;

public class Chariot implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.CHARIOT;
    private static final PieceAction PIECE_ACTION;
    private static final List<PieceType> UNCATCHABLE_PIECE_TYPES = List.of();

    static {
        final List<Rule> rules = List.of(
            new RuleWithTraces(List.of(new Movement(MAXIMUM_ROW, Direction.SOUTH))),
            new RuleWithTraces(List.of(new Movement(MAXIMUM_ROW, Direction.NORTH))),
            new RuleWithTraces(List.of(new Movement(MAXIMUM_COLUMN, Direction.EAST))),
            new RuleWithTraces(List.of(new Movement(MAXIMUM_COLUMN, Direction.WEST))));
        PIECE_ACTION = new PieceAction(rules);
    }

    private final TeamType teamType;

    public Chariot(final TeamType teamType) {
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
    public boolean isSameTypeAs(Piece other) {
        return PIECE_TYPE == other.getPieceType();
    }

    @Override
    public List<Position> calculateMovablePositions(final Position from,
        final BoardMediator boardMediator) {
        return PIECE_ACTION.calculateMovablePositions(from, boardMediator);
    }

    @Override
    public boolean canCatch(final Piece target) {
        return !UNCATCHABLE_PIECE_TYPES.contains(target.getPieceType()) && !target.isOnSameTeamAs(
            this);
    }
}
