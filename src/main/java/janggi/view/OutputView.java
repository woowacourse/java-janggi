package janggi.view;

import janggi.domain.board.Position;
import janggi.domain.game.PlayerResultDTO;
import janggi.domain.game.Side;
import janggi.domain.piece.PieceDTO;
import janggi.domain.board.BoardDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OutputView {
    private static final String PLAYER_NAME_NOTICE = "선수(%s) 플레이어의 이름을 입력하세요.";
    private static final String BOARD_SETTING_NOTICE = "장기판의 현황은 다음과 같습니다.";
    private static final String PLAYER_TURN_NOTICE = "(%s) 플레이어 %s 님의 턴입니다.";
    private static final String INPUT_PIECE_TO_MOVE_NOTICE = "이동할 기물의 좌표를 입력해주세요.";
    private static final String INPUT_TARGET_TO_MOVE_NOTICE = "기물을 이동할 좌표를 입력해주세요.";
    private static final String MOVE_POSITION_ROW_NOTICE = "좌표의 행을 입력해주세요.";
    private static final String MOVE_POSITION_COLUMN_NOTICE = "좌표의 열을 입력해주세요.";

    private static final String CONTINUE_GAME_NOTICE = "\n[안내] 진행 중인 게임이 발견되었습니다.";
    private static final String CONTINUE_GAME_QUESTION = "이전 게임을 이어서 진행하시겠습니까? (y / n)";
    private static final String RESUME_NOTICE = "\n이전 게임 데이터를 성공적으로 불러왔습니다. 게임을 재개합니다.";

    private static final String GAME_OVER_NOTICE = "\n========================== 게임 종료 ==========================";
    private static final String SCORE_RESULT_NOTICE = "\n-------------------------- [ 최종 기물 점수 결과 ] --------------------------";
    private static final String WINNER_NOTICE = "(%s) 플레이어 %s 님의 승리입니다.";
    private static final String TOTAL_SCORE_NOTICE = "(%s) 플레이어 %s 님의 총점은 %.1f점입니다.";

    private static final String EMPTY_CELL = "　　";
    private static final String COLUMN_INDEXES = "　　║　　０　　　　１　　　　２　　　　３　　　　４　　　　５　　　　６　　　　７　　　　８";
    private static final String DIVIDER = "　　║==========================================================================================";
    private static final String VERTICAL_LINE = "　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃";

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";

    public void printBoardSettingNotice() {
        printLine(BOARD_SETTING_NOTICE);
    }

    public void printBoardStatus(BoardDTO boardDto) {
        printBoardStatus(boardDto, null, null);
    }

    public void printBoardStatus(BoardDTO boardDto, Position selected) {
        printBoardStatus(boardDto, selected, null);
    }

    public void printBoardStatus(BoardDTO boardDto, Position selected, Set<Position> movables) {
        printLine(COLUMN_INDEXES);
        printLine(DIVIDER);
        for (int row = Position.BOARD_MIN_ROW; row <= Position.BOARD_MAX_ROW; row++) {
            renderRow(row, boardDto.piecePosition(), selected, movables);
            renderVerticalLine(row);
        }
        printLine(DIVIDER);
        printLine(COLUMN_INDEXES);
    }

    private void renderRow(int row, Map<Position, PieceDTO> status, Position selected, Set<Position> movables) {
        StringBuilder sb = new StringBuilder(toFullWidthRow(row) + "　║");
        for (int col = Position.BOARD_MIN_COLUMN; col <= Position.BOARD_MAX_COLUMN; col++) {
            Position current = new Position(row, col);
            sb.append(getFormattedCell(status, current, selected, movables));
            if (col < Position.BOARD_MAX_COLUMN) {
                sb.append("━");
            }
        }
        printLine(sb.toString());
    }

    private String getFormattedCell(Map<Position, PieceDTO> status, Position current, Position selected,
                                    Set<Position> movables) {
        String label = convertPieceToLabel(status.get(current));
        String cell = "［" + label + "］";

        // 선택된 기물 -> 파란색
        if (current.equals(selected)) {
            return applyColor(cell, ANSI_BLUE);
        }

        // 이동 가능한 경로 -> 초록색
        if (isMovable(current, movables)) {
            return applyColor(cell, ANSI_GREEN);
        }

        return cell;
    }

    private String convertPieceToLabel(PieceDTO vo) {
        if (vo == null) {
            return EMPTY_CELL;
        }
        return PieceLabelFormatter.toFullWidth(vo);
    }

    private boolean isMovable(Position current, Set<Position> movables) {
        return movables != null && movables.contains(current);
    }

    private String applyColor(String cell, String colorCode) {
        return colorCode + cell + ANSI_RESET;
    }

    private void renderVerticalLine(int row) {
        if (row < Position.BOARD_MAX_ROW) {
            printLine(VERTICAL_LINE);
        }
    }

    private String toFullWidthRow(int i) {
        return String.valueOf((char) ('０' + i));
    }

    public void printLine(String message) {
        System.out.println(message);
    }

    public void printPlayerNameNotice(String displayName) {
        printLine(String.format(PLAYER_NAME_NOTICE, displayName));
    }

    public void printPlayerTurnNotice(String playerName, String sideName) {
        printLine(String.format(PLAYER_TURN_NOTICE, sideName, playerName));
    }

    public void printMovePositionRowNotice() {
        printLine(MOVE_POSITION_ROW_NOTICE);
    }

    public void printMovePositionColumnNotice() {
        printLine(MOVE_POSITION_COLUMN_NOTICE);
    }

    public void printSelectPiecePosition() {
        printLine(INPUT_PIECE_TO_MOVE_NOTICE);
    }

    public void printSelectTargetPosition() {
        printLine(INPUT_TARGET_TO_MOVE_NOTICE);
    }

    public void printWinnerNotice(Side side, String playerName) {
        printLine(GAME_OVER_NOTICE);
        printLine(String.format(WINNER_NOTICE, side, playerName));
        printLine(SCORE_RESULT_NOTICE);
    }

    public void printTotalScores(List<PlayerResultDTO> results) {
        for (PlayerResultDTO result : results) {
            printLine(String.format(TOTAL_SCORE_NOTICE,
                    result.side(),
                    result.name(),
                    result.score()));
        }
    }

    public void printContinueGameNotice() {
        printLine(CONTINUE_GAME_NOTICE);
        printLine(CONTINUE_GAME_QUESTION);
    }

    public void printResumeNotice() {
        printLine(RESUME_NOTICE);
    }
}
