package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.board.Direction;
import java.util.List;
import java.util.stream.IntStream;

public class GeneralMoveStrategy implements MoveStrategy {

    private static final List<Direction> ALL_DIRECTIONS = List.of(
            Direction.UP, Direction.RIGHT_UP, Direction.RIGHT, Direction.RIGHT_DOWN,
            Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT, Direction.LEFT_UP
    );

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        Position anotherGeneralPosition = board.getAnotherGeneralPosition(from);

        return ALL_DIRECTIONS.stream()
                .map(from::move)
                .filter(Position::isInsideBoard)
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

        return IntStream.range(lowerGeneralColumn + 1, upperGeneralColumn)
                .mapToObj(column -> Position.of(column, row))
                .allMatch(board::isEmpty);
    }
}
