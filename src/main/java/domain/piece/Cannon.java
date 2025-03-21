package domain.piece;

import domain.BoardPosition;
import domain.Offset;
import domain.Piece;
import domain.PieceType;
import domain.Team;
import java.util.List;
import java.util.stream.Stream;

public class Cannon extends Piece {

    public Cannon(final Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    public List<Offset> findMovementRule(
            final BoardPosition before,
            final BoardPosition after
    ) {
        final Offset offset = after.calculateOffset(before);
        validateNotMove(offset);
        validateOffset(offset);

        return createMovementRule(offset);
    }

    @Override
    public boolean isObstacleCountAllowed(final int obstacleCount) {
        return obstacleCount == 1;
    }

    // TODO : Piece의 추상 메서드로 두어도 괜찮을 것 같다.
    private void validateOffset(final Offset offset) {
        if (isDiagonalMovement(offset) || hasOneMovement(offset)) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean isDiagonalMovement(final Offset offset) {
        return offset.x() != 0 && offset.y() != 0;
    }

    private boolean hasOneMovement(final Offset offset) {
        return (offset.x() == 0 && Math.abs(offset.y()) == 1) ||
                (Math.abs(offset.x()) == 1 && offset.y() == 0);
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
}
