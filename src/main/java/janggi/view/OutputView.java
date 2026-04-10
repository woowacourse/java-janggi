package janggi.view;

import static janggi.view.Message.GAME_DATA_INFO;

import janggi.dto.BoardDto;
import janggi.dto.GameSessionDto;
import janggi.dto.PieceDto;
import janggi.dto.PositionDto;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String EMPTY_CELL = "　　";
    private static final String COLUMN_INDEXES = "　　║　　０　　　　１　　　　２　　　　３　　　　４　　　　５　　　　６　　　　７　　　　８";
    private static final String DIVIDER = "　　║==========================================================================================";
    private static final String VERTICAL_LINE = "　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃";

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");

    public void printBoardSettingNotice() {
        printLine(Message.BOARD_SETTING_NOTICE);
    }

    public void printBoardStatus(BoardDto boardDto) {
        printBoardStatus(boardDto, null, Collections.emptyList());
    }

    public void printBoardStatus(BoardDto boardDto, PositionDto selected) {
        printBoardStatus(boardDto, selected, Collections.emptyList());
    }

    public void printBoardStatus(BoardDto boardDto, PositionDto selected, List<PositionDto> movables) {
        printLine(COLUMN_INDEXES);
        printLine(DIVIDER);
        for (int row = 0; row < 10; row++) {
            renderRow(row, boardDto.piecePosition(), selected, movables);
            renderVerticalLine(row);
        }
        printLine(DIVIDER);
        printLine(COLUMN_INDEXES);
    }

    private void renderRow(int row, Map<PositionDto, PieceDto> status, PositionDto selected,
                           List<PositionDto> movables) {
        StringBuilder sb = new StringBuilder(toFullWidthRow(row) + "　║");
        for (int col = 0; col < 9; col++) {
            PositionDto current = new PositionDto(row, col);
            sb.append(getFormattedCell(status, current, selected, movables));
            if (col < 8) {
                sb.append("━");
            }
        }
        printLine(sb.toString());
    }

    private String getFormattedCell(Map<PositionDto, PieceDto> status, PositionDto current, PositionDto selected,
                                    List<PositionDto> movables) {
        PieceDto piece = status.get(current);
        String baseCell = createBaseCell(piece);

        return applyColor(baseCell, piece, current, selected, movables);
    }

    private String createBaseCell(PieceDto piece) {
        if (piece == null) {
            return "［" + EMPTY_CELL + "］";
        }
        return "［" + piece.label() + "］";
    }

    private String applyColor(String cell, PieceDto piece, PositionDto current, PositionDto selected,
                              List<PositionDto> movables) {
        if (current.equals(selected)) {
            return ANSI_BLUE + cell + ANSI_RESET;
        }
        if (movables.contains(current)) {
            return ANSI_YELLOW + cell + ANSI_RESET;
        }

        return applySideColor(cell, piece);
    }

    private String applySideColor(String cell, PieceDto piece) {
        if (piece == null) {
            return cell;
        }

        if ("CHO".equals(piece.sideName())) {
            return ANSI_GREEN + cell + ANSI_RESET;
        }
        if ("HAN".equals(piece.sideName())) {
            return ANSI_RED + cell + ANSI_RESET;
        }

        return cell;
    }

    private void renderVerticalLine(int row) {
        if (row < 9) {
            printLine(VERTICAL_LINE);
        }
    }

    private String toFullWidthRow(int i) {
        return String.valueOf((char) ('０' + i));
    }

    public void printLine(String message) {
        System.out.println(message);
    }

    public void printNewLine() {
        printLine("");
    }

    public void printPlayerNameNotice(String displayName) {
        printLine(String.format(Message.PLAYER_NAME_NOTICE, displayName));
    }

    public void printPlayerTurnNotice(String playerName, String sideName, double playerScore) {
        printLine(String.format(Message.PLAYER_TURN_NOTICE, sideName, playerName, playerScore));
    }

    public void printMovePositionRowNotice() {
        printLine(Message.MOVE_POSITION_ROW_NOTICE);
    }

    public void printMovePositionColumnNotice() {
        printLine(Message.MOVE_POSITION_COLUMN_NOTICE);
    }

    public void printSelectPiecePosition() {
        printLine(Message.INPUT_PIECE_TO_MOVE_NOTICE);
    }

    public void printSelectTargetPosition() {
        printLine(Message.INPUT_TARGET_TO_MOVE_NOTICE);
    }

    public void printSelectGameData() {
        printLine(Message.GAME_DATA_SELECT_NOTICE);
    }

    public void printActiveGameInfo(GameSessionDto gameSessionDTO) {
        String formattedDate = gameSessionDTO.createdAt().format(DATE_FORMATTER);
        printLine(String.format(GAME_DATA_INFO, gameSessionDTO.gameId(), gameSessionDTO.choPlayerName(),
                gameSessionDTO.hanPlayerName(), gameSessionDTO.currentTurn(), formattedDate));
        printNewLine();
    }

    public void printGameNotExist() {
        printLine(Message.GAME_DATA_NOT_EXIST);
    }

    public void printGenerateGameData() {
        printLine(Message.GAME_DATA_GENERATE);
        printNewLine();
    }

    public void printSelectGameId() {
        printLine(Message.INPUT_TARGET_GAME_ID);
    }
}
