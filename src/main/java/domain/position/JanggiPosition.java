package domain.position;

import domain.direction.Direction;
import domain.position.vo.Column;
import domain.position.vo.Row;

import java.util.List;
import java.util.Objects;

public abstract class JanggiPosition {
    protected static final List<Direction> defaultDirections = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );

    private final Row row;
    private final Column column;

    protected JanggiPosition(final int row, final int col) {
        this.row = new Row(row);
        this.column = new Column(col);
    }

    abstract public boolean isCastle();

    abstract protected List<Direction> getLinkedDirections();

    public final boolean canMove(Direction direction) {
        return row.canMove(direction.dr) && column.canMove(direction.dc);
    }

    public final List<Direction> getLinkedRoadDirections() {
        return getLinkedDirections().stream().filter(this::canMove).toList();
    }

    public final JanggiPosition move(Direction direction) {
        final int nextRow = getRow() + direction.dr;
        final int nextCol = getCol() + direction.dc;
        return JanggiPositionFactory.of(nextRow, nextCol);
    }

    public final int getRow() {
        return row.value();
    }

    public final int getCol() {
        return column.value();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof JanggiPosition that)) return false;
        return Objects.equals(row, that.row) && Objects.equals(column, that.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
