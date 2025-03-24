package dto;

public record MoveCommandDTO(
        int sourceRow,
        int sourceColumn,
        String pieceName,
        int destinationRow,
        int destinationColumn
) {
    public static MoveCommandDTO from(String source, String pieceName, String destination) {
        int sourceRow = charToInt(source.charAt(0));
        int sourceColumn = charToInt(source.charAt(1));
        int destinationRow = charToInt(destination.charAt(0));
        int destinationColumn = charToInt(destination.charAt(1));
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
        if (value == 0) {
            value = 10;
        }
        return value;
    }
}
