package domain.movestrategy;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class GeneralMoveStrategy implements MoveStrategy {

    private static final List<Delta> ALL_DIRECTIONS = List.of(
            Delta.UP, Delta.RIGHT_UP, Delta.RIGHT, Delta.RIGHT_DOWN,
            Delta.DOWN, Delta.LEFT_DOWN, Delta.LEFT, Delta.LEFT_UP
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return ALL_DIRECTIONS.stream()
                .map(from::move)
                .filter(position -> !isDirected(position, pieces, from))
                .filter(position -> isNotAlly(position, pieces, from))
                .toList();
    }


    private boolean isDirected(Position nextPosition, Map<Position, Piece> pieces, Position from) {
        Optional<Position> oppositeGeneralPositionOpt = pieces.entrySet().stream()
                .filter(entry -> entry.getValue().getPieceType() == PieceType.GENERAL)
                .map(Entry::getKey)
                .filter(position -> !from.equals(position))
                .findFirst();

        if (oppositeGeneralPositionOpt.isEmpty()) {
            throw new IllegalStateException();
        }

        Position opposite = oppositeGeneralPositionOpt.get();

        if (nextPosition.row() != opposite.row()) {
            return false;
        }

        for (int column = Math.min(nextPosition.column(), opposite.column()) + 1;
             column < Math.max(nextPosition.column(), opposite.column());
             column++) {

            Position mid = Position.of(column, nextPosition.row());

            if (!pieces.containsKey(mid)) {
                continue;
            }
            if (mid.equals(from)) {
                continue;
            }

            return false;
        }

        return true;
    }

    private boolean isNotAlly(Position next, Map<Position, Piece> pieces, Position from) {
        if (!pieces.containsKey(next)) {
            return true;
        }

        return pieces.get(from).getTeam() != pieces.get(next).getTeam();
    }
}
