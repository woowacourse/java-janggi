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

    public Position move(Movement movement) {
        return new Position(column.move(movement.x()), row.move(movement.y()));
    }

    public boolean canMove(Movement movement) {
        if (column.canMove(movement.x()) && row.canMove(movement.y())) {
            return true;
        }
        return false;
    }

    public List<Position> findMoveablePositions(List<Movement> pieceMovements) {
        return pieceMovements.stream()
                .filter(this::canMove)
                .map(this::move)
                .toList();
    }
}
