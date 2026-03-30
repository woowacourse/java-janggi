package janggi.model.board.movement;

import janggi.model.board.moveResult.MoveResult;
import janggi.model.board.position.Position;
import janggi.model.board.moveResult.PositionPath;
import janggi.model.board.position.Row;
import java.util.ArrayList;
import java.util.List;

public class MaMovement implements Movement{

    private static final int ONE_STEP = 1;
    private static final int TWO_STEP = 2;

    @Override
    public MoveResult move(Position from, Position to) {
        int rowDiff = from.getRowDiff(to);
        int rowDistance = Math.abs(rowDiff);

        int columnDiff = from.getColumnDiff(to);
        int columnDistance = Math.abs(columnDiff);

        boolean isMaShape =
                (rowDistance == ONE_STEP && columnDistance == TWO_STEP)
                        || (rowDistance == TWO_STEP && columnDistance == ONE_STEP);

        if (!isMaShape) {
            throw new IllegalArgumentException("가로와 세로에 대해 하나는 1칸, 다른 하나는 2칸씩 떨어져 있어야 합니다.");
        }

        if (rowDistance > columnDistance) {
            return moveVerticallyAndDiagonally(from, to);
        }

        return moveHorizontallyAndDiagonally(from, to);
    }

    private MoveResult moveVerticallyAndDiagonally(Position from, Position to) {
        List<Position> between = new ArrayList<>();

        int fromValue = from.row().getValue();
        int toValue = to.row().getValue();

        int nextValue = fromValue + 1;

        if (fromValue > toValue) {
            nextValue = fromValue - 1;
        }

        between.add(new Position(
                Row.of(nextValue),
                from.column()
        ));

        return new MoveResult(
                new PositionPath(between),
                from,
                to
        );
    }

    private MoveResult moveHorizontallyAndDiagonally(Position from, Position to) {
        List<Position> between = new ArrayList<>();

        int fromValue = from.column().getValue();
        int toValue = to.column().getValue();

        int nextValue = fromValue + 1;

        if (fromValue < toValue) {
            nextValue = fromValue - 1;
        }

        between.add(new Position(
                Row.of(nextValue),
                from.column()
        ));

        return new MoveResult(
                new PositionPath(between),
                from,
                to
        );
    }
}
