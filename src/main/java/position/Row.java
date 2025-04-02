package position;

import java.util.Arrays;
import java.util.List;

public enum Row {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN;

    public static Row of(final String row) {
        return values()[Integer.parseInt(row) - 1];
    }

    public Row move(int amount) {
        if (!canMove(amount)) {
            throw new IllegalArgumentException("이동할 수 없는 행입니다.");
        }
        return values()[ordinal() + amount];
    }

    public boolean canMove(int amount) {
        if (ordinal() + amount >= values().length || ordinal() + amount < 0) {
            return false;
        }
        return true;
    }

    public List<Row> findBetweenRows(Row targetRow) {

        int min = Math.min(this.ordinal(), targetRow.ordinal());
        int max = Math.max(this.ordinal(), targetRow.ordinal());
        return Arrays.stream(values())
                .filter(row -> row.ordinal() > min && row.ordinal() < max)
                .toList();
    }

    public Row reverse() {
        int reversedIndex = values().length - 1 - this.ordinal();
        return values()[reversedIndex];
    }

    public boolean isInChoPalaceRow() {
        return this.ordinal() <= Row.THREE.ordinal();
    }

    public boolean isInHanPalaceRow() {
        return this.ordinal() >= Row.EIGHT.ordinal() && this.ordinal() <= Row.TEN.ordinal(); // EIGHT ~ TEN
    }
}
