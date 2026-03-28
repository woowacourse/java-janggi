package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.Movement;
import janggi.domain.movement.Rule;
import janggi.domain.movement.RuleWithTraces;
import janggi.domain.team.TeamType;
import java.util.List;

public class General implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.GENERAL;
    private static final PieceAction PIECE_ACTION;
    private static final List<PieceType> UNCATCHABLE_PIECE_TYPES = List.of();

    static {
        final List<Rule> rules = List.of(
                new RuleWithTraces(List.of(new Movement(1, Direction.UP_LEFT))),
                new RuleWithTraces(List.of(new Movement(1, Direction.UP))),
                new RuleWithTraces(List.of(new Movement(1, Direction.UP_RIGHT))),
                new RuleWithTraces(List.of(new Movement(1, Direction.LEFT))),
                new RuleWithTraces(List.of(new Movement(1, Direction.RIGHT))),
                new RuleWithTraces(List.of(new Movement(1, Direction.DOWN_LEFT))),
                new RuleWithTraces(List.of(new Movement(1, Direction.DOWN))),
                new RuleWithTraces(List.of(new Movement(1, Direction.DOWN_RIGHT))));
        PIECE_ACTION = new PieceAction(rules);
    }

    private final TeamType teamType;

    public General(final TeamType teamType) {
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
        return PIECE_ACTION.calculateMovablePositions(from, boardMediator);
    }

    @Override
    public boolean canKill(final Piece target) {
        return !UNCATCHABLE_PIECE_TYPES.contains(target.getPieceType()) && !target.belongsToTeam(teamType);
    }
}
