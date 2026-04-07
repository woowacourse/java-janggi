package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Column;
import janggi.domain.Position;
import janggi.domain.Row;

import java.util.List;

public class SangMoveStorage implements MoveStorage{
    private static final int FORWARD = 3;
    private static final int DIAGONAL = 2;

    @Override
    public boolean canMove(Position from, Position to, BoardView boardView) {
        if (isNotSangPattern(from, to)) {
            return false;
        }

        List<Position> myeokPath = findMyeokPath(from, to);

        return myeokPath.stream()
                .noneMatch(boardView::hasPieceAt);
    }

    private boolean isNotSangPattern(Position from, Position to) {
        int rowDiff = Math.abs(from.getRowValue() - to.getRowValue());
        int columnDiff = Math.abs(from.getColumnValue() - to.getColumnValue());

        return !((rowDiff == FORWARD && columnDiff == DIAGONAL) || (rowDiff == DIAGONAL && columnDiff == FORWARD));
    }

    private List<Position> findMyeokPath(Position from, Position to) {
        int rowDiff = to.getRowValue() - from.getRowValue();
        int columnDiff = to.getColumnValue() - from.getColumnValue();
        int signX = Integer.signum(rowDiff);
        int signY = Integer.signum(columnDiff);

        if (Math.abs(rowDiff) == FORWARD) {
            return List.of(
                    Position.of(Row.of(from.getRowValue() + signX), Column.of(from.getColumnValue())),
                    Position.of(Row.of(from.getRowValue() + (signX * 2)), Column.of(from.getColumnValue() + signY))
            );
        }

        return List.of(
                Position.of(Row.of(from.getRowValue()), Column.of(from.getColumnValue() + signY)),
                Position.of(Row.of(from.getRowValue() + signX), Column.of(from.getColumnValue() + (signY * 2)))
        );
    }
}
