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
    NINE;

    public Row move(int amount) {
        if (!canMove(amount)) {
            throw new IllegalArgumentException("이동할 수 없는 열입니다.");
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
                .filter(row -> row.ordinal() > min && row.ordinal() <= max)
                .toList();
    }
}
