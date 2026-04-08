package view;

import dto.GameRecordDto;
import dto.GameResultDto;
import dto.SavedGameDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViewFormatter {
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

    public String formatPossiblePosition(int number, int row, int col) {
        return String.format("%d. [%d,%d]%n", number, row, col);
    }

    public String formatColNumbers() {
        StringBuilder colNumbers = new StringBuilder("\n   ");
        for (int col = 1; col <= 9; col++) {
            colNumbers.append(COL_NUMBERS.get(col)).append("  ");
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

    public String formatSavedGames(SavedGameDto savedGameDto) {
        return String.format("- [%d번] 최종 수정 날짜 : %s%n", savedGameDto.gameId(),
                formatModifiedDate(savedGameDto.modifiedDate()));
    }

    private String formatModifiedDate(LocalDateTime modifiedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return modifiedDate.format(formatter);
    }

    public String formatGameRecord(GameRecordDto gameRecordDto) {
        return String.format("""
                        
                        ----------------
                        
                        [%d번]
                        승리 : %s
                        초나라 점수 : %.1f
                        한나라 점수 : %.1f
                        
                        """, gameRecordDto.gameId(),
                gameRecordDto.country(),
                gameRecordDto.choScore(),
                gameRecordDto.hanScore());
    }

    public String formatGameResult(GameResultDto gameResultDto) {
        return String.format("""
                        
                        ----------------
                        
                        승리 : %s
                        초나라 점수 : %.1f
                        한나라 점수 : %.1f
                        
                        ----------------
                        
                        """, gameResultDto.winner(),
                gameResultDto.choScore(),
                gameResultDto.hanScore());
    }
}
