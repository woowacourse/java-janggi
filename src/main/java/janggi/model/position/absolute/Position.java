package janggi.model.position.absolute;

import java.util.Set;

public record Position(
        Row row,
        Column column
) {
    private static final Set<Position> CHO_AREA = Set.of(
            new Position(Row.ZERO, Column.FOUR),
            new Position(Row.ZERO, Column.FIVE),
            new Position(Row.ZERO, Column.SIX),

            new Position(Row.NINE, Column.FOUR),
            new Position(Row.NINE, Column.FIVE),
            new Position(Row.NINE, Column.SIX),

            new Position(Row.EIGHT, Column.FOUR),
            new Position(Row.EIGHT, Column.FIVE),
            new Position(Row.EIGHT, Column.SIX)
    );

    private static final Set<Position> HAN_AREA = Set.of(
            new Position(Row.ONE, Column.FOUR),
            new Position(Row.ONE, Column.FIVE),
            new Position(Row.ONE, Column.SIX),

            new Position(Row.TWO, Column.FOUR),
            new Position(Row.TWO, Column.FIVE),
            new Position(Row.TWO, Column.SIX),

            new Position(Row.THREE, Column.FOUR),
            new Position(Row.THREE, Column.FIVE),
            new Position(Row.THREE, Column.SIX)
    );

    private static final Set<UndirectedLine> CHO_LINES = Set.of(
            // 내부 간선
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.EIGHT, Column.FIVE)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.EIGHT, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.NINE, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.ZERO, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.ZERO, Column.FIVE)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.ZERO, Column.FOUR)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.NINE, Column.FOUR)),
            new UndirectedLine(new Position(Row.NINE, Column.FIVE), new Position(Row.EIGHT, Column.FOUR)),

            // 외곽선
            new UndirectedLine(new Position(Row.NINE, Column.FOUR), new Position(Row.EIGHT, Column.FOUR)),
            new UndirectedLine(new Position(Row.NINE, Column.FOUR), new Position(Row.ZERO, Column.FOUR)),
            new UndirectedLine(new Position(Row.EIGHT, Column.FIVE), new Position(Row.EIGHT, Column.FOUR)),
            new UndirectedLine(new Position(Row.EIGHT, Column.FIVE), new Position(Row.EIGHT, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.SIX), new Position(Row.EIGHT, Column.SIX)),
            new UndirectedLine(new Position(Row.NINE, Column.SIX), new Position(Row.ZERO, Column.SIX)),
            new UndirectedLine(new Position(Row.ZERO, Column.FIVE), new Position(Row.ZERO, Column.FOUR)),
            new UndirectedLine(new Position(Row.ZERO, Column.FIVE), new Position(Row.ZERO, Column.SIX))
    );

    private static final Set<UndirectedLine> HAN_LINES = Set.of(
            // 내부 간선
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.ONE, Column.FIVE)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.ONE, Column.SIX)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.TWO, Column.SIX)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.THREE, Column.SIX)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.THREE, Column.FIVE)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.THREE, Column.FOUR)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.TWO, Column.FOUR)),
            new UndirectedLine(new Position(Row.TWO, Column.FIVE), new Position(Row.ONE, Column.FOUR)),

            // 외곽선
            new UndirectedLine(new Position(Row.ONE, Column.FOUR), new Position(Row.ONE, Column.FIVE)),
            new UndirectedLine(new Position(Row.ONE, Column.FIVE), new Position(Row.ONE, Column.SIX)),
            new UndirectedLine(new Position(Row.ONE, Column.FOUR), new Position(Row.TWO, Column.FOUR)),
            new UndirectedLine(new Position(Row.TWO, Column.FOUR), new Position(Row.THREE, Column.FOUR)),
            new UndirectedLine(new Position(Row.THREE, Column.FOUR), new Position(Row.THREE, Column.FIVE)),
            new UndirectedLine(new Position(Row.THREE, Column.FIVE), new Position(Row.THREE, Column.SIX)),
            new UndirectedLine(new Position(Row.ONE, Column.SIX), new Position(Row.TWO, Column.SIX)),
            new UndirectedLine(new Position(Row.TWO, Column.SIX), new Position(Row.THREE, Column.SIX))
    );

    public boolean isInSamePalaceWith(Position other) {
        return (CHO_AREA.contains(this) && CHO_AREA.contains(other))
                || (HAN_AREA.contains(this) && HAN_AREA.contains(other));
    }

    public boolean isAdjacentInPalaceWith(Position other) {
        return HAN_LINES.contains(new UndirectedLine(this, other))
                || CHO_LINES.contains(new UndirectedLine(this, other));
    }

    public int getRowDiff(Position other) {
        return this.row.getDistanceTo(other.row);
    }

    public int getColumnDiff(Position other) {
        return this.column.getDistanceTo(other.column);
    }

    public int getDistanceTo(Position other) {
        int rowDistance = Math.abs(this.row.getDistanceTo(other.row));
        int columnDistance = Math.abs(this.column.getDistanceTo(other.column));

        if (rowDistance > columnDistance) {
            return rowDistance;
        }

        return columnDistance;
    }

    public boolean isSameRow(Position other) {
        return this.row == other.row;
    }

    public boolean isSameColumn(Position other){
            return this.column == other.column;
        }
}
