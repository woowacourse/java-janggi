package domain.board;

import java.util.Arrays;
import java.util.Optional;

public enum Palace {
    HAN {
        @Override
        public boolean contains(Position position) {
            return isPalaceColumn(position.column()) && position.row().ordinal() <= HAN_ROW_BOUNDARY;
        }

        @Override
        public Position center() {
            return new Position(PALACE_CENTER_COLUMN, HAN_PALACE_CENTER_ROW);
        }
    },
    CHO {
        @Override
        public boolean contains(Position position) {
            return isPalaceColumn(position.column()) && position.row().ordinal() >= CHO_ROW_BOUNDARY;
        }

        @Override
        public Position center() {
            return new Position(PALACE_CENTER_COLUMN, CHO_PALACE_CENTER_ROW);
        }
    };

    private static final String OUTSIDE_PALACE_ERROR = "[ERROR] 궁성 밖으로 이동할 수 없습니다.";
    private final static int COLUMN_BOUNDARY_FROM = Column.D.ordinal();
    private final static int COLUMN_BOUNDARY_TO = Column.F.ordinal();
    private final static int HAN_ROW_BOUNDARY = Row.TWO.ordinal();
    private final static int CHO_ROW_BOUNDARY = Row.SEVEN.ordinal();
    private final static Column PALACE_CENTER_COLUMN = Column.E;
    private final static Row HAN_PALACE_CENTER_ROW = Row.ONE;
    private final static Row CHO_PALACE_CENTER_ROW = Row.EIGHT;

    public abstract boolean contains(Position position);

    public abstract Position center();

    public boolean isOnDiagonal(Position position) {
        if (!contains(position)) {
            return false;
        }
        Position c = center();
        int colDiff = Math.abs(position.column().ordinal() - c.column().ordinal());
        int rowDiff = Math.abs(position.row().ordinal() - c.row().ordinal());
        return colDiff == rowDiff;
    }

    public void validateContains(Position position) {
        if (!contains(position)) {
            throw new IllegalArgumentException(OUTSIDE_PALACE_ERROR);
        }
    }

    public static Palace requirePalace(Position position) {
        return of(position)
                .orElseThrow(() -> new IllegalArgumentException(OUTSIDE_PALACE_ERROR));
    }

    public void validateOnDiagonal(Position position) {
        if (!isOnDiagonal(position)) {
            throw new IllegalArgumentException("[ERROR] 궁성 대각선으로만 이동할 수 있습니다.");
        }
    }

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
