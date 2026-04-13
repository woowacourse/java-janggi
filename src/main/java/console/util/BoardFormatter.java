package console.util;

import static console.view.ViewMapper.SYMBOL_MAP;

import model.Team;
import model.piece.Piece;

public class BoardFormatter {

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    public static final String SPACE = "　";
    public static final String VERTICAL_LINE = "｜";

    public static final String[] COL_NUM = {"０", "１", "２", "３", "４", "５", "６", "７", "８"};
    public static final String[] ROW_NUM = {"０", "１", "２", "３", "４", "５", "６", "７", "８", "９"};

    private static final String HORIZON_LINE = "－";
    private static final String CORNER = "＋";
    private static final String EMPTY = "＊";

    private BoardFormatter() {
    }

    public static String formatHorizon(int colSize) {
        return SPACE + " " + CORNER + HORIZON_LINE.repeat(colSize * 2 + 1) + CORNER;
    }

    public static String formatSymbol(Piece piece) {
        if (piece == null) {
            return EMPTY;
        }
        String color = extractColor(piece.team());
        String symbol = SYMBOL_MAP.get(piece.type()).get(piece.team());
        return color + symbol + RESET;
    }

    private static String extractColor(Team team) {
        if (team == Team.HAN) {
            return RED;
        }
        return GREEN;
    }
}
