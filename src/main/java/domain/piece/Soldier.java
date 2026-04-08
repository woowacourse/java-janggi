package domain.piece;

import domain.Direction;
import domain.Offset;
import exception.ErrorMessage;
import domain.board.Position;

public final class Soldier extends SingleStepPiece {
    private final Direction backwardDirection; // 🌟 금지된 방향을 상태로 가짐

    public Soldier(Team team) {
        super(PieceType.SOLDIER, team);
        this.backwardDirection = team.getBackwardDirection();
    }

    @Override
    protected void validateMoveRule(Position from, Position to) {
        super.validateMoveRule(from, to);
        Offset offset = Offset.of(from, to);
        Direction direction = Direction.of(offset);

        if (direction.containsBackWardDirection(backwardDirection)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }
}
