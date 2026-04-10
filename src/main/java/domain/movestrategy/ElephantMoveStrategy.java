package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Position;

import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy implements MoveStrategy {

    private static final Map<Delta, List<Delta>> PATHS_BY_DESTINATION = Map.ofEntries(
            Map.entry(new Delta(-3, -2), List.of(Delta.UP, Delta.LEFT_UP)),
            Map.entry(new Delta(-3, 2), List.of(Delta.UP, Delta.RIGHT_UP)),

            Map.entry(new Delta(3, -2), List.of(Delta.DOWN, Delta.LEFT_DOWN)),
            Map.entry(new Delta(3, 2), List.of(Delta.DOWN, Delta.RIGHT_DOWN)),

            Map.entry(new Delta(-2, -3), List.of(Delta.LEFT, Delta.LEFT_UP)),
            Map.entry(new Delta(2, -3), List.of(Delta.LEFT, Delta.LEFT_DOWN)),

            Map.entry(new Delta(-2, 3), List.of(Delta.RIGHT, Delta.RIGHT_UP)),
            Map.entry(new Delta(2, 3), List.of(Delta.RIGHT, Delta.RIGHT_DOWN))
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        return PATHS_BY_DESTINATION.entrySet().stream()
                .filter(entry -> !isBlocked(from, entry.getValue(), board))
                .map(entry -> from.move(entry.getKey()))
                .filter(board::inBoard)
                .filter(destination -> !isAlly(from, destination, board))
                .toList();
    }

    @Override
    public List<Position> calculatePalaceMovablePositions(final Position from, final Board board) {
        return List.of();
    }


    private boolean isBlocked(final Position from, final List<Delta> paths, final Board board) {
        Position current = from;

        for (final Delta delta : paths) {
            current = current.move(delta);

            if (!board.inBoard(current)) {
                return true;
            }

            if (board.hasPiece(current)) {
                return true;
            }
        }
        return false;
    }
}
