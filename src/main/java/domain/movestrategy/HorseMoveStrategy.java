package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

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
    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        return PATH_BY_DESTINATION.entrySet().stream()
                .filter(entry -> !board.hasPiece(from.move(entry.getValue())))
                .map(entry -> from.move(entry.getKey()))
                .filter(destination -> !isAlly(from, destination, board))
                .toList();
    }
}
