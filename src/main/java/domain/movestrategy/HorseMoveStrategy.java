package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Position;

import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    private static final Map<Delta, Delta> PATH_BY_DESTINATION = Map.ofEntries(
            Map.entry(new Delta(-2, -1), Delta.UP),
            Map.entry(new Delta(-2, 1), Delta.UP),
            Map.entry(new Delta(-1, -2), Delta.LEFT),
            Map.entry(new Delta(1, -2), Delta.LEFT),
            Map.entry(new Delta(2, -1), Delta.DOWN),
            Map.entry(new Delta(2, 1), Delta.DOWN),
            Map.entry(new Delta(-1, 2), Delta.RIGHT),
            Map.entry(new Delta(1, 2), Delta.RIGHT)
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        return PATH_BY_DESTINATION.entrySet().stream()
                .filter(entry -> !board.hasPiece(from.move(entry.getValue())))
                .map(entry -> from.move(entry.getKey()))
                .filter(destination -> !isAlly(from, destination, board))
                .toList();
    }

    @Override
    public List<Position> calculatePalaceMovablePositions(final Position from, final Board board) {
        return List.of();
    }
}
