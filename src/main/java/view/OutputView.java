package view;

import domain.game.Turn;
import view.dto.BoardDto;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class OutputView {

    private static final String TURN_DIVIDER = "──────────────────────────";
    private static final String TURN_FORMAT = "  %s 차례입니다.";

    private static final String BOARD_COL_HEADER_INDENT = "    ";
    private static final String BOARD_COL_FORMAT = " %d  ";
    private static final String BOARD_DIVIDER = "─".repeat(36);
    private static final String BOARD_DIVIDER_INDENT = "    ";
    private static final String BOARD_ROW_FORMAT = "%2d │ ";

    private static final String GAME_END_DIVIDER = "==========================";
    private static final String GAME_END_MESSAGE = "게임이 종료되었습니다.";
    private static final String GAME_END_WINNER = "%s 진영이 승리하셨습니다!";
    private static final String SCORE_FORMAT = "%s 점수: %s";

    private static final int BOARD_CELL_WIDTH = 4;
    private static final Pattern ANSI_PATTERN = Pattern.compile("\\u001B\\[[;\\d]*m");
    private static final DecimalFormat SCORE_DECIMAL_FORMAT = new DecimalFormat("0.#");

    public void printTurn(Turn turn) {
        System.out.println();
        System.out.println(TURN_DIVIDER);
        System.out.printf(TURN_FORMAT + "%n", turn.getName());
        System.out.println(TURN_DIVIDER);
    }

    public void printBoard(BoardDto boardDto) {
        Map<List<Integer>, String> board = boardDto.board();

        System.out.print(BOARD_COL_HEADER_INDENT);
        for (int col = 1; col <= 9; col++) {
            System.out.printf(BOARD_COL_FORMAT, col);
        }
        System.out.println();

        System.out.print(BOARD_DIVIDER_INDENT);
        System.out.println(BOARD_DIVIDER);
        for (int row = 1; row <= 10; row++) {
            System.out.printf(BOARD_ROW_FORMAT, row);
            for (int col = 1; col <= 9; col++) {
                System.out.print(padCell(board.get(List.of(col, row))));
            }
            System.out.println();
        }
    }

    private String padCell(String coloredText) {
        String plain = ANSI_PATTERN.matcher(coloredText).replaceAll("");
        int visibleWidth = displayWidth(plain); // ANSI 제외, 한글 폭 반영
        int pad = Math.max(0, BOARD_CELL_WIDTH - visibleWidth);
        return coloredText + " ".repeat(pad);
    }

    private int displayWidth(String text) {
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            width += charWidth(text.charAt(i));
        }
        return width;
    }

    private int charWidth(char ch) {
        if (isWide(ch)) {
            return 2;
        }
        return 1;
    }

    private boolean isWide(char ch) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
        return block == Character.UnicodeBlock.HANGUL_SYLLABLES
                || block == Character.UnicodeBlock.HANGUL_JAMO
                || block == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || block == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION;
    }

    public void printGameEnd(Turn winner) {
        System.out.println();
        System.out.println(GAME_END_DIVIDER);
        System.out.println(GAME_END_MESSAGE);
        System.out.printf(GAME_END_WINNER + "%n", winner.getName());
        System.out.println(GAME_END_DIVIDER);
    }

    public void printScore(String team, double score) {
        System.out.printf(SCORE_FORMAT + "%n", team, formatScore(score));
    }

    private String formatScore(double score) {
        return SCORE_DECIMAL_FORMAT.format(score);
    }
}
