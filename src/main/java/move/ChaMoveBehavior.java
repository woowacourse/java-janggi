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
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition, Team team) {
        JanggiPosition smallerPosition = startPosition.getSmallerPosition(endPosition);
        JanggiPosition biggerPosition = startPosition.getBiggerPosition(endPosition);

        List<JanggiPosition> positions = new ArrayList<>();
        return calculateSameLineRoute(startPosition, endPosition, smallerPosition, biggerPosition, positions);
    }

    private List<JanggiPosition> calculateSameLineRoute(JanggiPosition startPosition, JanggiPosition endPosition, JanggiPosition minPosition,
                                                        JanggiPosition maxPosition, List<JanggiPosition> positions) {
        if (startPosition.isSameColumn(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP);
        }
        if (startPosition.isSameRow(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.RIGHT);
        }
        throw new InvalidMovePosition();
    }

    private List<JanggiPosition> calculateLegalRoute(JanggiPosition minPosition, JanggiPosition maxPosition, List<JanggiPosition> positions,
                                                     Direction direction) {
        while (!minPosition.equals(maxPosition)) {
            minPosition = minPosition.add(direction);
            positions.add(minPosition);
        }
        return Collections.unmodifiableList(positions);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CHA;
    }
}
