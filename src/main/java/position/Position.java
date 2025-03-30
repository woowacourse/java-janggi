package position;

import java.util.List;

public record Position(
        Column column,
        Row row
) {

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public static Position of(final String column, final String row) {
        return new Position(Column.of(column), Row.of(row));
    }

    public Position move(Movement movement) {
        return new Position(column.move(movement.x()), row.move(movement.y()));
    }

    public boolean canMove(Movement movement) {
        if (column.canMove(movement.x()) && row.canMove(movement.y())) {
            return true;
        }
        return false;
    }

    public Path findMovablePath(List<Movement> pieceMovements) {
        List<Position> positions = pieceMovements.stream()
                .filter(this::canMove)
                .map(this::move)
                .toList();
        return new Path(positions);
    }



    public boolean isStraight(Position toPosition) {
        if (column == toPosition.column || row == toPosition.row) {
            return true;
        }
        return false;

    }

    public Path findStraightPath(Position toPosition) {
        List<Position> positions;
        if (column == toPosition.column) {
            positions = row.findBetweenRows(toPosition.row)
                    .stream()
                    .map(row -> new Position(column, row))
                    .toList();
        }
        else {
            positions = column.findBetweenColumn(toPosition.column)
                    .stream()
                    .map(column -> new Position(column, row))
                    .toList();
        }
        return new Path(positions);
    }

    public Position reverse() {
        return new Position(column.reverse(), row.reverse());
    }

    public boolean onPalace() {
        return (row.isInChoPalaceRow() || row.isInHanPalaceRow()) && column.isInPalaceColumn();
    }

    public boolean isCenterOfPalace() {
        return (column == Column.E && row == Row.TWO) || (column == Column.E && row == Row.NINE);
    }
}
