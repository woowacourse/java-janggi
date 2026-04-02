package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Delta;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    private static final Map<Delta, Delta> PATH_BY_DESTINATION = Map.ofEntries(
            Map.entry(Delta.of(-2, -1), Delta.UP),
            Map.entry(Delta.of(-2, 1), Delta.UP),
            Map.entry(Delta.of(-1, -2), Delta.LEFT),
            Map.entry(Delta.of(1, -2), Delta.LEFT),
            Map.entry(Delta.of(2, -1), Delta.DOWN),
            Map.entry(Delta.of(2, 1), Delta.DOWN),
            Map.entry(Delta.of(-1, 2), Delta.RIGHT),
            Map.entry(Delta.of(1, 2), Delta.RIGHT)
    );

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {

        return PATH_BY_DESTINATION.entrySet().stream()
                .filter(entry -> board.isEmpty(from.move(entry.getValue())))
                .filter(entry -> board.isEmptyOrOpposite(from, from.move(entry.getKey())))
                .map(entry -> from.move(entry.getKey()))
                .filter(Position::isInside)
                .toList();
    }
}
