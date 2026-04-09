package domain.piece;

import domain.Direction;
import domain.Offset;
import domain.board.Palace;
import exception.ErrorMessage;
import domain.board.Position;

import java.util.Map;
import java.util.Optional;

public final class Soldier extends SingleStepPiece {
    private static final Map<Team, Direction> backwardDirections = Map.of(Team.CHO, Direction.DOWN, Team.HAN, Direction.UP);
    private final Direction backwardDirection;

    public Soldier(Team team) {
        super(PieceType.SOLDIER, team);
        this.backwardDirection = backwardDirections.get(team);
    }


    @Override
    protected void validateMoveRule(Position from, Position to, Optional<Palace> palace) {
        super.validateMoveRule(from, to, palace);
        Offset offset = Offset.of(from, to);
        Direction direction = Direction.of(offset);

        if (direction.containsBackWardDirection(backwardDirection)) {
            throw new IllegalStateException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }
}
