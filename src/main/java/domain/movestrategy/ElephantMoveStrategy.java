package domain.movestrategy;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy implements MoveStrategy {

    private static final Map<Position, List<Delta>> PATHS_BY_DESTINATION = Map.ofEntries(
            Map.entry(Position.of(-3, -2), List.of(Delta.up(), Delta.leftUp())),
            Map.entry(Position.of(-3, 2), List.of(Delta.up(), Delta.rightUp())),

            Map.entry(Position.of(3, -2), List.of(Delta.down(), Delta.leftDown())),
            Map.entry(Position.of(3, 2), List.of(Delta.down(), Delta.rightDown())),

            Map.entry(Position.of(-2, -3), List.of(Delta.left(), Delta.leftUp())),
            Map.entry(Position.of(2, -3), List.of(Delta.left(), Delta.leftDown())),

            Map.entry(Position.of(-2, 3), List.of(Delta.right(), Delta.rightUp())),
            Map.entry(Position.of(2, 3), List.of(Delta.right(), Delta.rightDown()))
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return PATHS_BY_DESTINATION.entrySet().stream()
                .filter(entry -> !isBlocked(from, entry.getValue(), pieces))
                .map(entry -> from.move(entry.getKey()))
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
