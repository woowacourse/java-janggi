package janggi.domain.position;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Position {
    private static final Map<String, Position> ALL_POSITION;

    static {
        ALL_POSITION = Row.all().stream()
                .flatMap(row -> Column.all().stream()
                        .map(column -> new Position(row, column)))
                .collect(Collectors.toMap(
                        p -> createKey(p.row.value(), p.column.value()),
                        p -> p
                ));
    }

    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        Position position = ALL_POSITION.get(createKey(row, column));
        if (position == null) {
            throw new IllegalArgumentException("잘못된 좌표입니다.");
        }
        return position;
    }

    public Optional<Position> move(Direction direction) {
        return Optional.ofNullable(
                ALL_POSITION.get((row.value() + direction.dr()) + "," + (column.value() + direction.dc()))
        );
    }

    private static String createKey(int row, int column) {
        return row + "," + column;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Position position)) return false;

        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(row);
        result = 31 * result + Objects.hashCode(column);
        return result;
    }
}
