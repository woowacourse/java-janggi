package domain.piece;

import domain.Team;
import domain.board.Offset;
import java.util.List;

public class Guard extends Piece {

    public Guard(final Team team) {
        super(PieceType.GUARD, team);
    }

    @Override
    protected List<Offset> createMovementRule(final Offset offset) {
        return List.of(offset);
    }

    @Override
    protected void validateOffset(final Offset offset) {
        if (!offset.hasOneMovement()) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return "사";
    }
}
