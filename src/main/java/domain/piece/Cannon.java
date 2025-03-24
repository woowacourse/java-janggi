package domain.piece;

import domain.Team;
import domain.board.Offset;
import java.util.List;
import java.util.stream.Stream;

public class Cannon extends Piece {

    private static final int ALLOWED_OBSTACLES_COUNT = 1;

    public Cannon(final Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    protected void validateOffset(final Offset offset) {
        if (offset.isDiagonalMovement() || offset.hasOneMovement()) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Offset> createMovementRule(final Offset offset) {
        final Offset unitDirection = offset.getUnitDirectionOffset();
        final int distance = getDistance(offset, unitDirection);

        return Stream.generate(() -> unitDirection)
                .limit(distance)
                .toList();
    }

    @Override
    public boolean isAllowedObstacles(final List<Piece> obstacles) {
        return obstacles.size() == ALLOWED_OBSTACLES_COUNT
                && PieceType.CANNON != obstacles.getLast().getPieceType();
    }

    @Override
    public boolean isCatchable(final Piece piece) {
        return PieceType.CANNON != piece.getPieceType();
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
        return "포";
    }
}
