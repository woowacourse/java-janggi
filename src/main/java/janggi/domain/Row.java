package janggi.domain;

public record Row(
        int row
) {

    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 10;

    public Row {
        validate(row);
    }

    private void validate(int row) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException(String.format("행은 %d부터 %d사이의 숫자입니다.", MIN_ROW, MAX_ROW));
        }
    }

}
