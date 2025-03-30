package position;

import java.util.Arrays;
import java.util.List;

public enum Column {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I;

    public static Column of(final String s) {
        return Column.valueOf(s);
    }

    public Column move(int amount) {
        if (!canMove(amount)) {
            throw new IllegalArgumentException("이동할 수 없는 열입니다. ");
        }
        return values()[ordinal() + amount];
    }


    public boolean canMove(int amount) {
        if (ordinal() + amount >= values().length || ordinal() + amount < 0) {
            return false;
        }
        return true;
    }

    public List<Column> findBetweenColumn(Column targetColumn) {
        int min = Math.min(this.ordinal(), targetColumn.ordinal());
        int max = Math.max(this.ordinal(), targetColumn.ordinal());
        return Arrays.stream(values())
                .filter(column -> column.ordinal() > min && column.ordinal() < max)
                .toList();
    }


    public Column reverse() {
        int reversedIndex = values().length - 1 - this.ordinal();
        return values()[reversedIndex];
    }

    public boolean isInPalaceColumn() {
        return this.ordinal() >= Column.D.ordinal() && this.ordinal() <= Column.F.ordinal();
    }
}
