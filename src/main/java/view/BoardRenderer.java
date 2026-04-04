package view;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final String VERTICAL_LINE = "   ｜　｜　｜　｜　｜　｜　｜　｜　｜";
    private static final String COLUMN_LABEL_PREFIX = "   ";
    private static final String COLUMN_LABEL_GAP = "  ";

    public String render(Board board) {
        List<String> lines = new ArrayList<>();

        for (int y = MIN_Y; y <= MAX_Y; y++) {
            lines.add(formatRowLabel(y) + " " + renderRow(board, y));

            if (y < MAX_Y) {
                lines.add(VERTICAL_LINE);
            }
        }

        lines.add(renderColumnLabels());

        return String.join(System.lineSeparator(), lines);
    }

    private String renderRow(Board board, int y) {
        List<String> cells = new ArrayList<>();

        for (int x = MIN_X; x <= MAX_X; x++) {
            Optional<Piece> piece = board.findPiece(new Position(x, y));
            cells.add(renderPiece(piece));
        }

        return String.join(HORIZONTAL_LINE, cells);
    }

    private String formatRowLabel(int y) {
        if (y == MAX_Y) {
            return "0 ";
        }

        return y + " ";
    }

    private String renderColumnLabels() {
        List<String> labels = new ArrayList<>();

        for (int x = MIN_X; x <= MAX_X; x++) {
            labels.add(String.valueOf(x));
        }

        return COLUMN_LABEL_PREFIX + String.join(COLUMN_LABEL_GAP, labels);
    }

    private String renderPiece(Optional<Piece> piece) {
        return piece.map(this::renderOccupiedPiece).orElse(EMPTY_CELL);
    }

    private String renderOccupiedPiece(Piece piece) {
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
}
