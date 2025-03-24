package move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import move.direction.Direction;
import piece.PieceType;
import piece.Team;
import piece.position.Position;

public class ChaMoveBehavior extends MoveBehavior {

    @Override
    public List<Position> calculateLegalRoute(Position startPosition, Position endPosition, Team team) {
        Position smallerPosition = startPosition.getSmallerPosition(endPosition);
        Position biggerPosition = startPosition.getBiggerPosition(endPosition);

        List<Position> positions = new ArrayList<>();
        return calculateSameLineRoute(startPosition, endPosition, smallerPosition, biggerPosition, positions);
    }

    private List<Position> calculateSameLineRoute(Position startPosition, Position endPosition, Position minPosition,
                                                  Position maxPosition, List<Position> positions) {
        if (startPosition.isSameColumn(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP);
        }
        if (startPosition.isSameRow(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.RIGHT);
        }
        throw new InvalidMovePosition();
    }

    private List<Position> calculateLegalRoute(Position minPosition, Position maxPosition, List<Position> positions,
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
