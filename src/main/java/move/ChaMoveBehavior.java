package move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import move.direction.Direction;
import piece.PieceType;
import piece.player.Team;
import piece.position.JanggiPosition;

public class ChaMoveBehavior extends JanggiMoveBehavior {

    private final List<Direction> diagonalCanMoveDirections = List.of(
            Direction.UP_RIGHT,
            Direction.UP_LEFT,
            Direction.DOWN_LEFT,
            Direction.DOWN_RIGHT
    );

    @Override
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                    Team team) {
        validateSamePosition(startPosition, endPosition);

        if (startPosition.equals(endPosition)) {
            throw new InvalidMovePosition();
        }
        JanggiPosition smallerPosition = startPosition.getSmallerPosition(endPosition);
        JanggiPosition biggerPosition = startPosition.getBiggerPosition(endPosition);

        List<JanggiPosition> positions = new ArrayList<>();
        return calculateCanMoveRoute(smallerPosition, biggerPosition, positions);
    }

    private void validateSamePosition(JanggiPosition startPosition, JanggiPosition endPosition) {
        if (startPosition.equals(endPosition)) {
            throw new InvalidMovePosition();
        }
    }

    private List<JanggiPosition> calculateLegalRoute(JanggiPosition minPosition, JanggiPosition maxPosition,
                                                     List<JanggiPosition> positions,
                                                     Direction direction) {
        while (!minPosition.equals(maxPosition) && minPosition.getSmallerPosition(maxPosition) == minPosition) {
            minPosition = minPosition.add(direction);
            positions.add(minPosition);
        }
        return Collections.unmodifiableList(positions);
    }

    private List<JanggiPosition> calculateLegalRoute(JanggiPosition minPosition, JanggiPosition maxPosition,
                                                     List<JanggiPosition> positions,
                                                     List<Direction> directions) {
        return directions.stream()
                .map((direction) -> calculateLegalRoute(minPosition, maxPosition, positions, direction))
                .findFirst()
                .filter(currentDirections -> currentDirections.getLast().equals(maxPosition))
                .orElseThrow(InvalidMovePosition::new);
    }

    private List<JanggiPosition> calculateCanMoveRoute(JanggiPosition minPosition,
                                                       JanggiPosition maxPosition, List<JanggiPosition> positions) {
        if (minPosition.isSameColumn(maxPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP);
        }
        if (minPosition.isSameRow(maxPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.RIGHT);
        }
        if (isDiagonalGungsungCase(minPosition, maxPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, diagonalCanMoveDirections);
        }
        throw new InvalidMovePosition();
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CHA;
    }
}
