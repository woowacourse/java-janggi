package domain.movestrategy;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy extends BasicMoveStrategy {

    private static final Map<Position, List<Delta>> PATHS_BY_DESTINATION = Map.ofEntries(
            Map.entry(Position.of(-3, -2), List.of(Delta.UP, Delta.LEFT_UP)),
            Map.entry(Position.of(-3, 2), List.of(Delta.UP, Delta.RIGHT_UP)),

            Map.entry(Position.of(3, -2), List.of(Delta.DOWN, Delta.LEFT_DOWN)),
            Map.entry(Position.of(3, 2), List.of(Delta.DOWN, Delta.RIGHT_DOWN)),

            Map.entry(Position.of(-2, -3), List.of(Delta.LEFT, Delta.LEFT_UP)),
            Map.entry(Position.of(2, -3), List.of(Delta.LEFT, Delta.LEFT_DOWN)),

            Map.entry(Position.of(-2, 3), List.of(Delta.RIGHT, Delta.RIGHT_UP)),
            Map.entry(Position.of(2, 3), List.of(Delta.RIGHT, Delta.RIGHT_DOWN))
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return PATHS_BY_DESTINATION.entrySet().stream()
                .filter(entry -> !isBlocked(from, entry.getValue(), pieces))
                .map(entry -> from.move(entry.getKey()))
                .filter(this::isInsideBoard)
                .toList();
    }

    private boolean isBlocked(final Position from, final List<Delta> paths, final Map<Position, Piece> pieces) {
        Position current = from;

        for (Delta delta : paths) {
            current = current.move(delta);
            if (pieces.containsKey(current)) {
                return true;
            }
        }
        return false;
    }
}
