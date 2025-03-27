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

    public Position move(Movement movement) {
        return new Position(column.move(movement.x()), row.move(movement.y()));
    }

    public boolean canMove(Movement movement) {
        if (column.canMove(movement.x()) && row.canMove(movement.y())) {
            return true;
        }
        return false;
    }

    public List<Position> findMovablePositions(List<Movement> pieceMovements) {
        return pieceMovements.stream()
                .filter(this::canMove)
                .map(this::move)
                .toList();
    }


    public boolean isStraight(Position toPosition) {
        if (column == toPosition.column || row == toPosition.row) {
            return true;
        }
        return false;

    }

    public List<Position> findStraightPositions(Position toPosition) {
        List<Position> positions = new ArrayList<>();
        if (column == toPosition.column) {
            return row.findBetweenRows(toPosition.row)
                    .stream()
                    .map(row -> new Position(column, row))
                    .toList();
        }
        return column.findBetweenColumn(toPosition.column)
                .stream()
                .map(column -> new Position(column, row))
                .toList();
    }
}
