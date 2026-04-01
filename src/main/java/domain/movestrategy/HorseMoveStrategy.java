package domain.movestrategy;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.board.Position;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy extends BasicMoveStrategy {

    private static final Map<Position, Delta> PATH_BY_DESTINATION = Map.ofEntries(
            Map.entry(Position.of(-2, -1), Delta.UP),
            Map.entry(Position.of(-2, 1), Delta.UP),
            Map.entry(Position.of(-1, -2), Delta.LEFT),
            Map.entry(Position.of(1, -2), Delta.LEFT),
            Map.entry(Position.of(2, -1), Delta.DOWN),
            Map.entry(Position.of(2, 1), Delta.DOWN),
            Map.entry(Position.of(-1, 2), Delta.RIGHT),
            Map.entry(Position.of(1, 2), Delta.RIGHT)
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return PATH_BY_DESTINATION.entrySet().stream()
                .filter(entry -> !pieces.containsKey(from.move(entry.getValue())))
                .map(entry -> from.move(entry.getKey()))
                .filter(this::isInsideBoard)
                .filter(position -> isEmptyOrOpposite(from, position, pieces))
                .toList();
    }
}
