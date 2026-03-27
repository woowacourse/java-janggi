package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.Movement;
import janggi.domain.movement.Rule;
import janggi.domain.movement.RuleWithTraces;
import janggi.domain.team.TeamType;
import java.util.List;

public class Soldier implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.SOLDIER;
    private static final PieceAction RED_PIECE_ACTION;
    private static final PieceAction BLUE_PIECE_ACTION;
    private static final List<PieceType> UNCATCHABLE_PIECE_TYPES = List.of();

    static {
        final List<Rule> redRules = List.of(
            new RuleWithTraces(List.of(new Movement(1, Direction.valueOf(0, -1)))),
            new RuleWithTraces(List.of(new Movement(1, Direction.valueOf(0, 1)))),
            new RuleWithTraces(List.of(new Movement(1, Direction.valueOf(1, 0)))));
        final List<Rule> blueRules = List.of(
            new RuleWithTraces(List.of(new Movement(1, Direction.valueOf(0, -1)))),
            new RuleWithTraces(List.of(new Movement(1, Direction.valueOf(0, 1)))),
            new RuleWithTraces(List.of(new Movement(1, Direction.valueOf(-1, 0)))));
        RED_PIECE_ACTION = new PieceAction(redRules);
        BLUE_PIECE_ACTION = new PieceAction(blueRules);
    }

    private final TeamType teamType;

    public Soldier(final TeamType teamType) {
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
        if (teamType == TeamType.RED) {
            return RED_PIECE_ACTION.calculateMovablePositions(from, boardMediator);
        }
        return BLUE_PIECE_ACTION.calculateMovablePositions(from, boardMediator);
    }

    @Override
    public boolean canKill(final Piece target) {
        return !UNCATCHABLE_PIECE_TYPES.contains(target.getPieceType()) && !target.belongsToTeam(
            teamType);
    }
}
