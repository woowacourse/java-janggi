package position;

import java.util.ArrayList;
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
    public boolean isDiagonal(Position toPosition) {
        return Math.abs(column.ordinal() - toPosition.column().ordinal()) ==
                Math.abs(row.ordinal() - toPosition.row().ordinal());
    }
    public Path findPalaceDiagonalPath(Position toPosition) {
        List<Position> diagonalPositions = createDiagonalPath(toPosition);
        validatePassesThroughCenter(diagonalPositions, toPosition);
        return new Path(diagonalPositions);
    }

    private List<Position> createDiagonalPath(Position toPosition) {
        List<Row> betweenRows = row.findBetweenRows(toPosition.row());
        List<Column> betweenColumns = column.findBetweenColumn(toPosition.column());
        List<Position> path = new ArrayList<>();
        for (int i = 0; i < betweenRows.size(); i++) {
            path.add(new Position(betweenColumns.get(i), betweenRows.get(i)));
        }
        return path;
    }

    private void validatePassesThroughCenter(List<Position> path, Position toPosition) {
        boolean passedThroughCenter = this.isCenterOfPalace() || toPosition.isCenterOfPalace()
                || path.stream().anyMatch(Position::isCenterOfPalace);
        if (!passedThroughCenter) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
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
