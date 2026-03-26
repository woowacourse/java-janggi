package janggi.view;

import janggi.domain.board.Position;
import janggi.domain.piece.PieceVO;
import janggi.util.PieceLabelMapper;
import janggi.dto.BoardDTO;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String EMPTY_CELL = "　　";
    private static final String COLUMN_INDEXES = "　　║　　０　　　　１　　　　２　　　　３　　　　４　　　　５　　　　６　　　　７　　　　８";
    private static final String DIVIDER = "　　║==========================================================================================";
    private static final String VERTICAL_LINE = "　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃";

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";

    public void printBoardSettingNotice() {
        printLine(Message.BOARD_SETTING_NOTICE);
    }

    public void printBoardStatus(BoardDTO boardDto) {
        printBoardStatus(boardDto, null, null);
    }

    public void printBoardStatus(BoardDTO boardDto, Position selected) {
        printBoardStatus(boardDto, selected, null);
    }

    public void printBoardStatus(BoardDTO boardDto, Position selected, List<Position> movables) {
        printLine(COLUMN_INDEXES);
        printLine(DIVIDER);
        for (int row = 0; row < 10; row++) {
            renderRow(row, boardDto.piecePosition(), selected, movables);
            renderVerticalLine(row);
        }
        printLine(DIVIDER);
        printLine(COLUMN_INDEXES);
    }

    private void renderRow(int row, Map<Position, PieceVO> status, Position selected, List<Position> movables) {
        StringBuilder sb = new StringBuilder(toFullWidthRow(row) + "　║");
        for (int col = 0; col < 9; col++) {
            Position current = new Position(row, col);
            sb.append(getFormattedCell(status, current, selected, movables));
            if (col < 8) sb.append("━");
        }
        printLine(sb.toString());
    }

    private String getFormattedCell(Map<Position, PieceVO> status, Position current, Position selected, List<Position> movables) {
        PieceVO vo = status.get(current);
        String label = (vo == null) ? EMPTY_CELL : PieceLabelMapper.toFullWidth(vo);
        String cell = "［" + label + "］";
        if (current.equals(selected)) {
            return ANSI_BLUE + cell + ANSI_RESET;
        }
        if (movables != null && movables.contains(current)) {
            return ANSI_GREEN + cell + ANSI_RESET;
        }
        return cell;
    }

    private void renderVerticalLine(int row) {
        if (row < 9) printLine(VERTICAL_LINE);
    }

    private String toFullWidthRow(int i) {
        return String.valueOf((char) ('０' + i));
    }

    public void printLine(String message) {
        System.out.println(message);
    }

    public void printPlayerNameNotice(String displayName) {
        printLine(String.format(Message.PLAYER_NAME_NOTICE, displayName));
    }

    public void printPlayerTurnNotice(String playerName, String sideName) {
        printLine(String.format(Message.PLAYER_TURN_NOTICE, sideName, playerName));
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

    public void printPieceNotExist() {
        printLine(Message.TARGET_POSITION_IS_NOT_MOVEABLE);
    }

    public void printNotOwnPiece() {
        printLine(Message.TARGET_PIECE_IS_NOT_OWNED);
    }
}
