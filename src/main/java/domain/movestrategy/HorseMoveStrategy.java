package domain.movestrategy;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Position;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    private static final Map<Direction, Direction> PATH_BY_DESTINATION = Map.ofEntries(
            Map.entry(Direction.of(-2, -1), Direction.UP),
            Map.entry(Direction.of(-2, 1), Direction.UP),
            Map.entry(Direction.of(-1, -2), Direction.LEFT),
            Map.entry(Direction.of(1, -2), Direction.LEFT),
            Map.entry(Direction.of(2, -1), Direction.DOWN),
            Map.entry(Direction.of(2, 1), Direction.DOWN),
            Map.entry(Direction.of(-1, 2), Direction.RIGHT),
            Map.entry(Direction.of(1, 2), Direction.RIGHT)
    );

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {

        return PATH_BY_DESTINATION.entrySet().stream()
                .filter(entry -> board.isEmpty(from.move(entry.getValue())))
                .filter(entry -> board.isEmptyOrOpposite(from, from.move(entry.getKey())))
                .map(entry -> from.move(entry.getKey()))
                .filter(Position::isInsideBoard)
                .toList();
    }
}
