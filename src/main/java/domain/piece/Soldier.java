package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Offset;

import java.util.List;

public final class Soldier extends SingleStepPiece {
    private final Direction backwardDirection; // 🌟 금지된 방향을 상태로 가짐

    public Soldier(Team team) {
        super(PieceType.SOLDIER, team);
        this.backwardDirection = team.getBackwardDirection();
    }

    @Override
    public List<Offset> getPathOffset(Offset offset) {
        super.getPathOffset(offset);
        if (offset.equals(backwardDirection.getOffset())) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
        return List.of();
    }
}
