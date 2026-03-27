package domain.movestrategy;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    private static final Map<Position, Delta> PATH_BY_DESTINATION = Map.ofEntries(
            Map.entry(Position.of(-2, -1), Delta.up()),
            Map.entry(Position.of(-2, 1), Delta.up()),
            Map.entry(Position.of(-1, -2), Delta.left()),
            Map.entry(Position.of(1, -2), Delta.left()),
            Map.entry(Position.of(2, -1), Delta.down()),
            Map.entry(Position.of(2, 1), Delta.down()),
            Map.entry(Position.of(-1, 2), Delta.right()),
            Map.entry(Position.of(1, 2), Delta.right())
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return PATH_BY_DESTINATION.entrySet().stream()
                .filter(entry -> !pieces.containsKey(from.move(entry.getValue())))
                .map(entry -> from.move(entry.getKey()))
                .toList();
    }
}
