package janggi.model.position;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum Row {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    HAN_BACK(10);

    private final int value;

    Row(int value) {
        this.value = value;
    }

    public static Row of(int rowNumber) {
        if (rowNumber == 0) {
            return HAN_BACK;
        }
        return Arrays.stream(values())
                .filter(row -> row.value == rowNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 행 번호입니다."));
    }

    public Row moved(int displacement) {
        int nextValue = this.value + displacement;

        if (nextValue > HAN_BACK.value || nextValue < ONE.value) {
            throw new IllegalArgumentException("보드 밖으로는 이동할 수 없습니다.");
        }

        return Row.of(nextValue);
    }

    public List<Row> to(Row other) {
        if (this.value > other.value) {
            return Arrays.stream(values())
                    .filter(row -> row.value >= other.value && row.value <= this.value)
                    .sorted(Comparator.comparingInt((Row row) -> row.value).reversed())
                    .toList();
        }
        return Arrays.stream(values())
                .filter(row -> row.value >= this.value && row.value <= other.value)
                .sorted(Comparator.comparingInt(row -> row.value))
                .toList();
    }

    public int getDistance(Row other) {
        return this.value - other.value;
    }

    public int getDisplayName() {
        if (this == HAN_BACK) {
            return 0;
        }
        return this.value;
    }

    public int getValue() {
        return this.value;
    }
}
