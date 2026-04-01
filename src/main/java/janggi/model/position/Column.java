package janggi.model.position;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum Column {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    private final int value;

    Column(int value) {
        this.value = value;
    }

    public static Column of(int columnNumber) {
        return Arrays.stream(values())
                .filter(column -> column.value == columnNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 열 번호입니다."));
    }

    public Column moved(int displacement) {
        int nextValue = this.value + displacement;

        if (nextValue > NINE.value || nextValue < ONE.value) {
            throw new IllegalArgumentException("보드 밖으로는 이동할 수 없습니다.");
        }

        return Column.of(nextValue);
    }

    public List<Column> to(Column other) {
        if (this.value > other.value) {
            return Arrays.stream(values())
                    .filter(column -> column.value >= other.value && column.value <= this.value)
                    .sorted(Comparator.comparingInt((Column column) -> column.value).reversed())
                    .toList();
        }
        return Arrays.stream(values())
                .filter(column -> column.value >= this.value && column.value <= other.value)
                .sorted(Comparator.comparingInt(column -> column.value))
                .toList();
    }

    public int getDistance(Column other) {
        return this.value - other.value;
    }

    public int getValue() {
        return this.value;
    }
}
