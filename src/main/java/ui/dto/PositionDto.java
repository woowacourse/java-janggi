package ui.dto;

import domain.position.Position;

public class PositionDto {
    private static final String COLUMN_SHOULD_BE_ALPHABETIC = "Coumn은 a~i 사이의 값을 입력해야 합니다.";
    private final int row;
    private final int column;

    public PositionDto(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static PositionDto toDto(String input) {
        char columnInfo = getColumnInfo(input);

        int columnValue = (int) columnInfo - 'a' + 1;
        int rowValue = Integer.parseInt(input.substring(1));

        return new PositionDto(rowValue, columnValue);
    }

    private static char getColumnInfo(String input) {
        char columnInfo = input.charAt(0);
        if (!Character.isAlphabetic(columnInfo)) {
            throw new IllegalArgumentException(COLUMN_SHOULD_BE_ALPHABETIC);
        }
        return columnInfo;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Position toDomain() {
        return Position.of(row, column);
    }
}
