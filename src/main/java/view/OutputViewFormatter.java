package view;

import java.util.List;

public class OutputViewFormatter {
    private static final String PREFIX_ERROR_MESSAGE = "[ERROR]";
    private static final String EMPTY_PIECE = "＋";
    private static final List<String> COL_NUMBERS = List.of("", "１", "２", "３", "４", "５", "６", "７", "８", "９");

    public String formatCountry(String country) {
        return String.format("%n---%n%n차례 : %s", country);
    }

    public String formatEmptyPiece() {
        return String.format("%-3s", EMPTY_PIECE);
    }

    public String formatPiece(String pieceColor, String pieceName) {
        return String.format("%s%-3s%s", pieceColor, pieceName, PieceColor.getColorCode(PieceColor.NONE.name()));
    }

    public String formatChangeTurn(String countryName) {
        return String.format("%n차례 : %s", countryName);
    }

    public String formatErrorMessage(String message) {
        return String.format("%s %s", PREFIX_ERROR_MESSAGE, message);
    }

    public String formatPossiblePositionHeader(String pieceName) {
        return String.format("%n['%s' 기물의 현재 좌표 목록]%n", pieceName);
    }

    public String formatPossiblePosition(int number, int x, int y) {
        return String.format("%d. [%d,%d]%n", number, x, y);
    }

    public String formatColNumbers() {
        StringBuilder colNumbers = new StringBuilder("   ");
        for (int y = 1; y <= 9; y++) {
            colNumbers.append(COL_NUMBERS.get(y)).append("  ");
        }
        colNumbers.append('\n');

        return colNumbers.toString();
    }

    public String formatRowNumber(int number) {
        return String.format("%2d|", number);
    }

    public String formatHorizontalLine() {
        return "  +-------------------------------+\n";
    }

    public String formatRightVerticalLine() {
        return "|\n";
    }

    public String formatGameWinner(String countryName) {
        return String.format("승리 : %s%n", countryName);
    }
}
