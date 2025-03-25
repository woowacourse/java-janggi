package move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import move.direction.Direction;
import piece.PieceType;
import piece.Team;
import piece.position.JanggiPosition;

public class ChaMoveBehavior extends MoveBehavior {

    @Override
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                    Team team) {
        JanggiPosition smallerPosition = startPosition.getSmallerPosition(endPosition);
        JanggiPosition biggerPosition = startPosition.getBiggerPosition(endPosition);

        List<JanggiPosition> positions = new ArrayList<>();
        return calculateCanMoveRoute(smallerPosition, biggerPosition, positions);
    }

    private List<JanggiPosition> calculateLegalRoute(JanggiPosition minPosition, JanggiPosition maxPosition,
                                                     List<JanggiPosition> positions,
                                                     Direction direction) {
        while (!minPosition.equals(maxPosition) && minPosition.getSmallerPosition(maxPosition) == minPosition) {
            minPosition = minPosition.add(direction);
            positions.add(minPosition);
        }
        if (!minPosition.equals(maxPosition)) {
            throw new InvalidMovePosition();
        }
        return Collections.unmodifiableList(positions);
    }

    private List<JanggiPosition> calculateCanMoveRoute(JanggiPosition minPosition,
                                                       JanggiPosition maxPosition, List<JanggiPosition> positions) {
        if (minPosition.isSameColumn(maxPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP);
        }
        if (minPosition.isSameRow(maxPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.RIGHT);
        }
        if (isDiagonalGungCase(minPosition, maxPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP_RIGHT);
        }
        throw new InvalidMovePosition();
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CHA;
    }
}
