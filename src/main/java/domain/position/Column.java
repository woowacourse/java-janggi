package domain.position;

public record Column(int column) {

    public static final int MIN_COLUMN = 1;
    public static final int MAX_COLUMN = 9;

    public Column {
        validateColumn(column);
    }

    public boolean isOutBoundColumn(int column){
        return column < MIN_COLUMN || column > MAX_COLUMN;
    }

    private void validateColumn(int column) {
        if (isOutBoundColumn(column)) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }

}
