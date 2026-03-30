package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Offset;

import java.util.List;

public final class HanSoldier extends SingleStepPiece {
    public HanSoldier(Team team) {
        super(PieceType.SOLDIER, team);
    }

    @Override
    public List<Offset> getPathPositions(Offset offset) {
        super.getPathPositions(offset);
        boolean isMoveBack = offset.equals(Direction.UP.getOffset());
        if (isMoveBack) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
        return List.of();
    }
}
