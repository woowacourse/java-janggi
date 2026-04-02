package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Delta;
import java.util.List;
import java.util.stream.IntStream;

public class GeneralMoveStrategy extends BasicMoveStrategy {

    private static final List<Delta> ALL_DIRECTIONS = List.of(
            Delta.UP, Delta.RIGHT_UP, Delta.RIGHT, Delta.RIGHT_DOWN,
            Delta.DOWN, Delta.LEFT_DOWN, Delta.LEFT, Delta.LEFT_UP
    );

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        Position anotherGeneralPosition = board.getAnotherGeneralPosition(from);

        return ALL_DIRECTIONS.stream()
                .map(from::move)
                .filter(Position::isInside)
                .filter(position -> board.isEmptyOrOpposite(from, position))
                .filter(position -> !areGeneralsFacingEachOther(board, position, anotherGeneralPosition))
                .toList();
    }

    private boolean areGeneralsFacingEachOther(
            final Board board,
            final Position generalPosition,
            final Position anotherGeneralPosition
    ) {
        if (generalPosition.row() != anotherGeneralPosition.row()) {
            return false;
        }
        int lowerGeneralColumn = Math.min(generalPosition.column(), anotherGeneralPosition.column());
        int upperGeneralColumn = Math.max(generalPosition.column(), anotherGeneralPosition.column());
        int row = generalPosition.row();

        return IntStream.range(lowerGeneralColumn, upperGeneralColumn)
                .mapToObj(column -> Position.of(column, row))
                .anyMatch(board::isEmpty);
    }
}
