package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.board.Direction;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy implements MoveStrategy {

    private static final Map<Direction, List<Direction>> PATHS_BY_DESTINATION = Map.ofEntries(
            Map.entry(Direction.of(-3, -2), List.of(Direction.UP, Direction.LEFT_UP)),
            Map.entry(Direction.of(-3, 2), List.of(Direction.UP, Direction.RIGHT_UP)),

            Map.entry(Direction.of(3, -2), List.of(Direction.DOWN, Direction.LEFT_DOWN)),
            Map.entry(Direction.of(3, 2), List.of(Direction.DOWN, Direction.RIGHT_DOWN)),

            Map.entry(Direction.of(-2, -3), List.of(Direction.LEFT, Direction.LEFT_UP)),
            Map.entry(Direction.of(2, -3), List.of(Direction.LEFT, Direction.LEFT_DOWN)),

            Map.entry(Direction.of(-2, 3), List.of(Direction.RIGHT, Direction.RIGHT_UP)),
            Map.entry(Direction.of(2, 3), List.of(Direction.RIGHT, Direction.RIGHT_DOWN))
    );

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        return PATHS_BY_DESTINATION.entrySet().stream()
                .filter(entry -> !isBlocked(board, from, entry.getValue()))
                .filter(entry -> board.isEmptyOrOpposite(from, from.move(entry.getKey())))
                .map(entry -> from.move(entry.getKey()))
                .filter(Position::isInsideBoard)
                .toList();
    }

    private boolean isBlocked(final Board board, final Position from, final List<Direction> paths) {
        Position current = from;
        for (Direction path : paths) {
            current = current.move(path);
            if (!board.isEmpty(current)) {
                return true;
            }
        }
        return false;
    }
}
