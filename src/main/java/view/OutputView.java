package view;

import dto.BoardViewSnapshot;

import java.util.List;
import java.util.Map;

public class OutputView {

    private static final int COLUMN_SIZE = 9;
    private static final int ROW_SIZE = 10;

    private static final String TURN_DIVIDER = "──────────────────────────";
    private static final String TURN_FORMAT = "  %s 차례입니다.";
    private static final String BOARD_INDENT = "     ";
    private static final String BOARD_COL_FORMAT = "  %d ";
    private static final String BOARD_DIVIDER = "─".repeat(36);
    private static final String BOARD_ROW_FORMAT = "%2d │ ";
    private static final String BOARD_CELL_FORMAT = " %s ";
    private static final String GAME_END_DIVIDER = "==========================";
    private static final String GAME_END_MESSAGE = "게임이 종료되었습니다.";
    private static final String GAME_END_WINNER = "%s 진영이 승리하셨습니다!";
    private static final String SCORE_HEADER = "=== 최종 점수 ===";
    private static final String CHO_SCORE_FORMAT = "초: %.1f점";
    private static final String HAN_SCORE_FORMAT = "한: %.1f점";

    public void printTurn(String turnName) {
        System.out.println();
        System.out.println(TURN_DIVIDER);
        System.out.printf(TURN_FORMAT + "%n", turnName);
        System.out.println(TURN_DIVIDER);
    }

    public void printBoard(BoardViewSnapshot boardViewSnapshot) {
        Map<List<Integer>, String> board = boardViewSnapshot.board();

        System.out.print(BOARD_INDENT);
        for (int col = 1; col <= COLUMN_SIZE; col++) {
            System.out.printf(BOARD_COL_FORMAT, col);
        }
        System.out.println();

        System.out.print(BOARD_INDENT);
        System.out.println(BOARD_DIVIDER);

        for (int row = 1; row <= ROW_SIZE; row++) {
            System.out.printf(BOARD_ROW_FORMAT, row);
            for (int col = 1; col <= COLUMN_SIZE; col++) {
                System.out.printf(BOARD_CELL_FORMAT, board.get(List.of(col, row)));
            }
            System.out.println();
        }
    }

    public void printGameEnd(String winnerName) {
        System.out.println();
        System.out.println(GAME_END_DIVIDER);
        System.out.println(GAME_END_MESSAGE);
        System.out.printf(GAME_END_WINNER + "%n", winnerName);
        System.out.println(GAME_END_DIVIDER);
    }

    public void printScore(double choScore, double hanScore) {
        System.out.println(SCORE_HEADER);
        System.out.printf(CHO_SCORE_FORMAT + "%n", choScore);
        System.out.printf(HAN_SCORE_FORMAT + "%n", hanScore);
    }
}
