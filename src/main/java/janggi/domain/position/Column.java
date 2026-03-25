package janggi.domain.position;

public record Column(
        int column
) {

    private static final int MIN_COLUMN = 1;
    private static final int MAX_COLUMN = 9;

    public Column {
        validate(column);
    }

    private void validate(int column) {
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException(String.format("열은 %d부터 %d사이의 숫자입니다.", MIN_COLUMN, MAX_COLUMN));
        }
    }

}
