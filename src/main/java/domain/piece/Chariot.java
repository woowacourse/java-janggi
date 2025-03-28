package domain.piece;

import domain.board.Offset;
import domain.board.movement.Movement;
import domain.janggi.Team;
import java.util.List;
import java.util.stream.Stream;

public class Chariot extends Piece {

    public Chariot(final Team team) {
        super(PieceType.CHARIOT, team);
    }

    @Override
    protected void validateMovement(final Movement movement) {
        if (!movement.isMoveOnLine()) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Offset> createMovementRule(final Offset offset) {
        final Offset unitDirection = offset.getUnitDirectionOffset();
        int distance = getDistance(offset, unitDirection);

        return Stream.generate(() -> unitDirection)
                .limit(distance)
                .toList();
    }

    private int getDistance(final Offset offset, final Offset unitDirection) {
        Offset tempOffset = Offset.origin();
        int distance = 0;
        while (!tempOffset.equals(offset)) {
            tempOffset = tempOffset.plus(unitDirection);
            distance++;
        }
        return distance;
    }
}
