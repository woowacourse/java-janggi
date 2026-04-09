package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Column;
import janggi.domain.Position;
import janggi.domain.Row;

public class MaMoveStorage implements MoveStorage{
    private static final int FORWARD = 2;
    private static final int DIAGONAL = 1;

    @Override
    public boolean canMove(Position from, Position to, BoardView boardView) {
        if (isNotMaPattern(from, to)) {
            return false;
        }

        Position myeok = findMyeok(from, to);
        return !boardView.hasPieceAt(myeok);
    }

    private boolean isNotMaPattern(Position from, Position to) {
        int rowDiff = Math.abs(from.getRowValue() - to.getRowValue());
        int columnDiff = Math.abs(from.getColumnValue() - to.getColumnValue());

        return !((rowDiff == FORWARD && columnDiff == DIAGONAL) || (rowDiff == DIAGONAL && columnDiff == FORWARD));
    }

    private Position findMyeok(Position from, Position to) {
        int rowDiff = to.getRowValue() - from.getRowValue();
        int columnDiff = to.getColumnValue() - from.getColumnValue();


        if (Math.abs(rowDiff) == FORWARD) {
            return Position.of(Row.of(from.getRowValue() + Integer.signum(rowDiff)), Column.of(from.getColumnValue()));
        }

        return Position.of(Row.of(from.getRowValue()), Column.of(from.getColumnValue() + Integer.signum(columnDiff)));
    }
}
