package domain.piece;

import domain.BoardPosition;
import domain.Offset;
import domain.Piece;
import domain.PieceType;
import domain.Team;
import java.util.List;
import java.util.stream.IntStream;

public class Chariot extends Piece {

    public Chariot(final Team team) {
        super(PieceType.CHARIOT, team);
    }

    @Override
    public List<Offset> findMovementRule(
            final BoardPosition before,
            final BoardPosition after
    ) {
        final Offset offset = after.calculateOffset(before);
        validateNotMove(offset);
        validateChariotOffset(offset);

        return createMovementRule(offset);
    }

    private List<Offset> createMovementRule(final Offset offset) {
        final Offset unitDirection = offset.getUnitDirectionOffset();

        int distance = getDistance(offset, unitDirection);

        return IntStream.rangeClosed(0, distance)
                .mapToObj(value -> unitDirection)
                .toList();
    }

    private int getDistance(final Offset offset, final Offset unitDirection) {
        Offset tempOffset = Offset.origin();
        int distance = 0;
        while (tempOffset != offset) {
            tempOffset = tempOffset.plus(unitDirection);
            distance++;
        }
        return distance;
    }

    private void validateChariotOffset(final Offset offset) {
        if (offset.x() != 0 && offset.y() != 0) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }
}
