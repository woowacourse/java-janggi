package domain.piece;

import domain.board.BoardPosition;
import domain.board.Offset;
import domain.Team;
import java.util.List;
import java.util.stream.Stream;

public class Chariot extends Piece {

    public Chariot(final Team team) {
        super(team);
    }

    @Override
    public List<Offset> findMovementRule(
            final BoardPosition before,
            final BoardPosition after
    ) {
        final Offset offset = after.calculateOffset(before);
        validateOffset(offset);

        return createMovementRule(offset);
    }

    @Override
    public boolean isAllowedObstacles(final List<Piece> obstacles) {
        return obstacles.isEmpty();
    }

    @Override
    public boolean isCatchable(final Piece piece) {
        return true;
    }

    private void validateOffset(final Offset offset) {
        if (offset.isDiagonalMovement()) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private List<Offset> createMovementRule(final Offset offset) {
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

    @Override
    public String toString() {
        return "차";
    }
}
