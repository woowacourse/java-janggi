package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.MoveRule;
import janggi.domain.movement.Movement;
import janggi.domain.movement.SlidingMoveRule;
import janggi.domain.team.TeamType;
import java.util.List;

public class Soldier implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.SOLDIER;
    private static final PieceAction RED_PIECE_ACTION;
    private static final PieceAction BLUE_PIECE_ACTION;

    static {
        final List<MoveRule> redMovementStrategies = List.of(
                new SlidingMoveRule(new Movement(1, Direction.LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.RIGHT)),
                new SlidingMoveRule(new Movement(1, Direction.DOWN)));
        final List<MoveRule> blueMovementStrategies = List.of(
                new SlidingMoveRule(new Movement(1, Direction.LEFT)),
                new SlidingMoveRule(new Movement(1, Direction.RIGHT)),
                new SlidingMoveRule(new Movement(1, Direction.UP)));
        RED_PIECE_ACTION = new PieceAction(redMovementStrategies);
        BLUE_PIECE_ACTION = new PieceAction(blueMovementStrategies);
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
            return RED_PIECE_ACTION.calculateMovablePositions(from, teamType, boardMediator);
        }
        return BLUE_PIECE_ACTION.calculateMovablePositions(from, teamType, boardMediator);
    }

    @Override
    public boolean canKill(final Piece target) {
        return !target.belongsToTeam(teamType);
    }
}
