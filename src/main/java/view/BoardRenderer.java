package view;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class BoardRenderer {
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final int MIN_X = 1;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 10;

    private static final String EMPTY_CELL = "＋";
    private static final String HORIZONTAL_LINE = "－";
    private static final String VERTICAL_LINE_DEFAULT = "   ｜　｜　｜　｜　｜　｜　｜　｜　｜";
    private static final String VERTICAL_LINE_PALACE_TOP = "   ｜　｜　｜　｜＼｜／｜　｜　｜　｜";
    private static final String VERTICAL_LINE_PALACE_BOTTOM = "   ｜　｜　｜　｜／｜＼｜　｜　｜　｜";

    public String render(Board board) {
        List<String> lines = new ArrayList<>();

        lines.add(renderXAxis());

        for (int y = MIN_Y; y <= MAX_Y; y++) {
            lines.add(formatRowLabel(y) + " " + renderRow(board, y));

            if (y < MAX_Y) {
                lines.add(getVerticalLine(y));
            }
        }

        return String.join(System.lineSeparator(), lines);
    }

    private String getVerticalLine(int y) {
        if (y == 1 || y == 8) {
            return VERTICAL_LINE_PALACE_TOP;
        }

        if (y == 2 || y == 9) {
            return VERTICAL_LINE_PALACE_BOTTOM;
        }

        return VERTICAL_LINE_DEFAULT;
    }

    private String renderRow(Board board, int y) {
        List<String> cells = new ArrayList<>();

        for (int x = MIN_X; x <= MAX_X; x++) {
            Piece piece = board.findPiece(new Position(x, y)).orElse(null);
            cells.add(renderPiece(piece));
        }

        return String.join(HORIZONTAL_LINE, cells);
    }

    private String formatRowLabel(int y) {
        if (y == MAX_Y) {
            return "10";
        }

        return y + " ";
    }

    private String renderPiece(Piece piece) {
        if (piece == null) {
            return EMPTY_CELL;
        }

        String symbol = switch (piece.type()) {
            case GENERAL -> renderGeneral(piece.camp());
            case SOLDIER -> renderSoldier(piece.camp());
            case GUARD -> "士";
            case CHARIOT -> "車";
            case CANNON -> "包";
            case HORSE -> "馬";
            case ELEPHANT -> "象";
        };

        return applyColor(piece.camp(), symbol);
    }

    private String renderGeneral(Camp camp) {
        if (camp == Camp.CHO) {
            return "楚";
        }

        return "漢";
    }

    private String renderSoldier(Camp camp) {
        if (camp == Camp.CHO) {
            return "卒";
        }

        return "兵";
    }

    private String applyColor(Camp camp, String symbol) {
        if (camp == Camp.CHO) {
            return ANSI_GREEN + symbol + ANSI_RESET;
        }

        return ANSI_RED + symbol + ANSI_RESET;
    }

    private String renderXAxis() {
        return "   １　２　３　４　５　６　７　８　９";
    }
}
