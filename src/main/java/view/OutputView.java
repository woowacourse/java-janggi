package view;

import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import dto.JanggiGameResultResponseDto;
import dto.PieceDto;

import java.util.Map;

public class OutputView {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 10;
    private static final int MIN_COLUMN = 1;
    private static final int MAX_COLUMN = 9;
    private static final int CELL_WIDTH = 4;
    private static final int LEFT_PADDING = 1;
    private static final String EMPTY_POINT = " ";
    private static final String EMPTY_GAP = "  ";
    private static final String VERTICAL = "│";
    private static final String ROW_LABEL_FORMAT = "%2d ";

    public static void printBoard(BoardResponseDto nowBoardState) {
        Map<Position, PieceDto> state = nowBoardState.state();
        System.out.println();
        printRows(state);
        System.out.println(buildColumnLabels());
        System.out.println();
    }

    public static void printSideChoiceResult(Side side) {
        System.out.println();
        System.out.println("당신은 " + side.getName() + "입니다.");
        System.out.println();
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public static void printSide(Side currentTurnSide) {
        System.out.println(currentTurnSide.getName() + "진영 차례 입니다.");
    }

    public static void printIsJangGun() {
        System.out.println("장군!");
    }

    public static void printScoreBothSide(JanggiGameResultResponseDto janggiGameResultResponseDto) {
        System.out.println("# 점수출력 #");
        System.out.println(janggiGameResultResponseDto.cho() + " : " + janggiGameResultResponseDto.choScore());
        System.out.println(janggiGameResultResponseDto.han() + " : " + janggiGameResultResponseDto.hanScore());
        System.out.println("승리한 진영: " + janggiGameResultResponseDto.winSide());
    }

    public static void printWinSide(Side side) {
        System.out.println(side.getName() + "진영이 승리하였습니다.");
    }

    public static void printLoadGame() {
        System.out.println("진행하던 게임을 불러오는 중 입니다.");
    }

    public static void printCreateNewGame() {
        System.out.println("기존에 진행하던 게임이 없어 새로운 게임을 생성합니다.");
    }

    private static void printRows(Map<Position, PieceDto> state) {
        for (int row = MAX_ROW; row >= MIN_ROW; row--) {
            System.out.println(buildRowLine(state, row));
            if (row > MIN_ROW) System.out.println(buildBetweenRowLine(row));
        }
    }

    private static String buildRowLine(Map<Position, PieceDto> state, int row) {
        StringBuilder builder = new StringBuilder(String.format(ROW_LABEL_FORMAT, row));
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) builder.append(renderPoint(state, row, column));
        return builder.toString();
    }

    private static String buildBetweenRowLine(int upperRow) {
        StringBuilder builder = new StringBuilder("   ");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++)
            builder.append(renderBetweenPoint(upperRow, column));
        return builder.toString();
    }

    private static String buildColumnLabels() {
        StringBuilder builder = new StringBuilder("   ");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) builder.append(renderColumnLabel(column));
        return builder.toString();
    }

    private static String renderPoint(Map<Position, PieceDto> state, int row, int column) {
        PieceDto piece = state.get(Position.of(row, column));
        String pointSymbol = pointSymbolOf(piece, row, column);
        String styledPoint = stylePoint(piece, pointSymbol);
        return padLeft() + styledPoint + renderHorizontal(pointSymbol, column);
    }

    private static String renderBetweenPoint(int upperRow, int column) {
        return padLeft() + renderVertical(upperRow, column) + renderGap(upperRow, column);
    }

    private static String pointSymbolOf(PieceDto piece, int row, int column) {
        if (piece == null) return boardSymbol(row, column);
        return piece.pieceTypeName();
    }

    private static String stylePoint(PieceDto piece, String pointSymbol) {
        if (piece == null) return pointSymbol;
        return colorize(piece.side(), pointSymbol);
    }

    private static String renderHorizontal(String point, int column) {
        if (column == MAX_COLUMN) return "";
        return "─".repeat(horizontalWidth(point));
    }

    private static String boardSymbol(int row, int column) {
        if (row == MAX_ROW && column == MIN_COLUMN) return "┌";
        if (row == MAX_ROW && column == MAX_COLUMN) return "┐";
        if (row == MIN_ROW && column == MIN_COLUMN) return "└";
        if (row == MIN_ROW && column == MAX_COLUMN) return "┘";
        if ((row == MAX_ROW || row == 3) && column == 5) return "┬";
        if ((row == 8 || row == MIN_ROW) && column == 5) return "┴";
        if ((row == 9 || row == 2) && column == 4) return "├";
        if ((row == 9 || row == 2) && column == 6) return "┤";
        if (row == MAX_ROW || row == MIN_ROW) return "┼";
        if (column == MIN_COLUMN) return "├";
        if (column == MAX_COLUMN) return "┤";
        return "┼";
    }

    private static String renderVertical(int upperRow, int column) {
        if (isPalaceDiagonalCenter(upperRow, column)) return EMPTY_POINT;
        return VERTICAL;
    }

    private static String renderGap(int upperRow, int column) {
        if (column == MAX_COLUMN) return "";
        if (upperRow == 10 && column == 4) return "╲ ";
        if (upperRow == 10 && column == 5) return "╱ ";
        if (upperRow == 9 && column == 4) return "╱ ";
        if (upperRow == 9 && column == 5) return "╲ ";
        if (upperRow == 3 && column == 4) return "╲ ";
        if (upperRow == 3 && column == 5) return "╱ ";
        if (upperRow == 2 && column == 4) return "╱ ";
        if (upperRow == 2 && column == 5) return "╲ ";
        return EMPTY_GAP;
    }

    private static boolean isPalaceDiagonalCenter(int upperRow, int column) {
        return (upperRow == 10 || upperRow == 9 || upperRow == 3 || upperRow == 2) && column == 5;
    }

    private static String colorize(Side side, String value) {
        return ansiColor(side) + value + ANSI_RESET;
    }

    private static String ansiColor(Side side) {
        return side.getColor().replace("\\u001B", "\u001B");
    }

    private static String renderColumnLabel(int column) {
        String label = String.valueOf(column);
        return padLeft() + label + " ".repeat(CELL_WIDTH - LEFT_PADDING - label.length());
    }

    private static String padLeft() {
        return " ".repeat(LEFT_PADDING);
    }

    private static int horizontalWidth(String point) {
        return CELL_WIDTH - LEFT_PADDING - displayWidth(point);
    }

    private static int displayWidth(String value) {
        int width = 0;
        for (int index = 0; index < value.length(); index++) {
            char current = value.charAt(index);
            if (current == '\u001B') {
                index = skipAnsi(value, index);
                continue;
            }
            width += isWideCharacter(current) ? 2 : 1;
        }
        return width;
    }

    private static int skipAnsi(String value, int index) {
        int current = index + 1;
        while (current < value.length() && value.charAt(current) != 'm') current++;
        return current;
    }

    private static boolean isWideCharacter(char value) {
        return value >= '\u1100' && value <= '\u11FF'
                || value >= '\u2E80' && value <= '\uA4CF'
                || value >= '\uAC00' && value <= '\uD7A3'
                || value >= '\uF900' && value <= '\uFAFF'
                || value >= '\uFE10' && value <= '\uFE6F'
                || value >= '\uFF00' && value <= '\uFF60'
                || value >= '\uFFE0' && value <= '\uFFE6';
    }
}
