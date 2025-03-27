package dto;

public record MoveCommandDTO(
        int sourceRow,
        int sourceColumn,
        String pieceName,
        int destinationRow,
        int destinationColumn
) {

    public static final int ROW_INDEX = 0;
    public static final int COLUMN_INDEX = 1;
    public static final int ZERO_ROW = 0;
    public static final int VALUE_OF_ZERO_ROW = 10;

    public static MoveCommandDTO from(String source, String pieceName, String destination) {
        int sourceRow = charToInt(source.charAt(ROW_INDEX));
        int sourceColumn = charToInt(source.charAt(COLUMN_INDEX));
        int destinationRow = charToInt(destination.charAt(ROW_INDEX));
        int destinationColumn = charToInt(destination.charAt(COLUMN_INDEX));
        return new MoveCommandDTO(
                sourceRow,
                sourceColumn,
                pieceName,
                destinationRow,
                destinationColumn
        );
    }

    public static int charToInt(char ch) {
        int value = Character.getNumericValue(ch);
        if (value == ZERO_ROW) {
            value = VALUE_OF_ZERO_ROW;
        }
        return value;
    }
}
