package view;

public class PositionDto {
    private final int startRow;
    private final int startColumn;
    private final int destinationRow;
    private final int destinationColumn;

    private PositionDto(int startRow, int startColumn, int destinationRow, int destinationColumn) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
    }

    public static PositionDto toDto(String startInfo, String destinationInfo) {
        char startColumnInfo = startInfo.charAt(0);
        int startColumnValue = (int) startColumnInfo - 'a' + 1;
        int startRowValue = Integer.parseInt(startInfo.substring(1));

        char destinationColumnInfo = destinationInfo.charAt(0);
        int destinationColumnValue = (int) destinationColumnInfo - 'a' + 1;
        int destinationRowValue = Integer.parseInt(destinationInfo.substring(1));

        return new PositionDto(startRowValue, startColumnValue, destinationRowValue, destinationColumnValue);
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getDestinationRow() {
        return destinationRow;
    }

    public int getDestinationColumn() {
        return destinationColumn;
    }
}
