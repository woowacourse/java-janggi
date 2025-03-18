import java.util.Arrays;

public enum Column {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    ;

    private final int value;

    Column(int value) {
        this.value = value;
    }

    public static Column from(int value) {
        return Arrays.stream(Column.values())
                .filter(col -> col.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 숫자에 맞는 Column 없습니다."));
    }


    public int getValue() {
        return value;
    }
}
