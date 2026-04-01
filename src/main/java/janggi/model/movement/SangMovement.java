package janggi.model.movement;

import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.ArrayList;
import java.util.List;

public class SangMovement implements Movement{

    private static final int TWO_STEP = 2;
    private static final int THREE_STEP = 3;

    @Override
    public PositionPath move(Position from, Position to) {
        int rowDiff = from.getRowDiff(to);
        int rowDistance = Math.abs(rowDiff);

        int columnDiff = from.getColumnDiff(to);
        int columnDistance = Math.abs(columnDiff);

        boolean isSangShape =
                (rowDistance == TWO_STEP && columnDistance == THREE_STEP)
                        || (rowDistance == THREE_STEP && columnDistance == TWO_STEP);

        if (!isSangShape) {
            throw new IllegalArgumentException("가로와 세로에 대해 하나는 2칸, 다른 하나는 3칸씩 떨어져 있어야 합니다.");
        }

        if (rowDistance > columnDistance) {
            return moveVerticallyAndDiagonally(from, to);
        }

        return moveHorizontallyAndDiagonally(from, to);
    }


    private PositionPath moveVerticallyAndDiagonally(Position from, Position to) {
        List<Position> between = new ArrayList<>();

        int rowStep = 1;
        if (from.row().getValue() > to.row().getValue()) {
            rowStep = -1;
        }

        int columnStep = 1;
        if (from.column().getValue() > to.column().getValue()) {
            columnStep = -1;
        }

        int firstRow = from.row().getValue() + rowStep;
        between.add(new Position(
                Row.of(firstRow),
                from.column()
        ));

        int secondRow = firstRow + rowStep;
        int secondColumn = from.column().getValue() + columnStep;
        between.add(new Position(
                Row.of(secondRow),
                Column.of(secondColumn)
        ));

        return new PositionPath(between);
    }

    private PositionPath moveHorizontallyAndDiagonally(Position from, Position to) {
        List<Position> between = new ArrayList<>();

        int rowStep = 1;
        if (from.row().getValue() > to.row().getValue()) {
            rowStep = -1;
        }

        int columnStep = 1;
        if (from.column().getValue() > to.column().getValue()) {
            columnStep = -1;
        }

        int firstColumn = from.column().getValue() + columnStep;
        between.add(new Position(
                from.row(),
                Column.of(firstColumn)
        ));

        int secondRow = from.row().getValue() + rowStep;
        int secondColumn = firstColumn + columnStep;
        between.add(new Position(
                Row.of(secondRow),
                Column.of(secondColumn)
        ));

        return new PositionPath(between);
    }
}
