package domain.board;

import java.util.Arrays;
import java.util.Optional;

public enum Palace {
    HAN {
        @Override
        public boolean contains(Position position) {
            return isPalaceColumn(position.column()) && position.row().ordinal() <= HAN_ROW_BOUNDARY;
        }
    },
    CHO {
        @Override
        public boolean contains(Position position) {
            return isPalaceColumn(position.column()) && position.row().ordinal() >= CHO_ROW_BOUNDARY;
        }
    };

    private final static int COLUMN_BOUNDARY_FROM = Column.D.ordinal();
    private final static int COLUMN_BOUNDARY_TO = Column.F.ordinal();
    private final static int HAN_ROW_BOUNDARY = Row.TWO.ordinal();
    private final static int CHO_ROW_BOUNDARY = Row.SEVEN.ordinal();

    public abstract boolean contains(Position position);

    public static Optional<Palace> of(Position position) {
        return Arrays.stream(values())
                .filter(palace -> palace.contains(position))
                .findFirst();
    }

    public static boolean isInAnyPalace(Position position) {
        return of(position).isPresent();
    }

    private static boolean isPalaceColumn(Column column) {
        return column.ordinal() >= COLUMN_BOUNDARY_FROM && column.ordinal() <= COLUMN_BOUNDARY_TO;
    }
}
