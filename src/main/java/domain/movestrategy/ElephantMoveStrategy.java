package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Delta;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy extends BasicMoveStrategy {

    private static final Map<Delta, List<Delta>> PATHS_BY_DESTINATION = Map.ofEntries(
            Map.entry(Delta.of(-3, -2), List.of(Delta.UP, Delta.LEFT_UP)),
            Map.entry(Delta.of(-3, 2), List.of(Delta.UP, Delta.RIGHT_UP)),

            Map.entry(Delta.of(3, -2), List.of(Delta.DOWN, Delta.LEFT_DOWN)),
            Map.entry(Delta.of(3, 2), List.of(Delta.DOWN, Delta.RIGHT_DOWN)),

            Map.entry(Delta.of(-2, -3), List.of(Delta.LEFT, Delta.LEFT_UP)),
            Map.entry(Delta.of(2, -3), List.of(Delta.LEFT, Delta.LEFT_DOWN)),

            Map.entry(Delta.of(-2, 3), List.of(Delta.RIGHT, Delta.RIGHT_UP)),
            Map.entry(Delta.of(2, 3), List.of(Delta.RIGHT, Delta.RIGHT_DOWN))
    );

    @Override
    public List<Position> getMovablePositions(Board board, Position from) {
        return PATHS_BY_DESTINATION.entrySet().stream()
                .filter(entry -> !isBlocked(board, from, entry.getValue()))
                .filter(entry -> board.isEmptyOrOpposite(from, from.move(entry.getKey())))
                .map(entry -> from.move(entry.getKey()))
                .filter(Position::isInside)
                .toList();
    }

    private boolean isBlocked(Board board, Position from, List<Delta> paths) {
        Position current = from;

        for (Delta delta : paths) {
            current = current.move(delta);
            if (!board.isEmpty(current)) {
                return true;
            }
        }
        return false;
    }
}
