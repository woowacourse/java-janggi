package janggi.position;

public enum Row {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    ;

    public static Row convert(String rowIndex) {
        return values()[Integer.parseInt(rowIndex)];
    }

    public boolean canMove(int step) {
        final var movedIndex = ordinal() + step;
        return values().length > movedIndex && movedIndex >= 0;
    }

    public Row move(int step) {
        if (canMove(step)) {
            return values()[ordinal() + step];
        }

        throw new IllegalArgumentException("[ERROR] 장기판 내에서만 이동할 수 있습니다.");
    }
}
