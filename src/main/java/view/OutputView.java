package view;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MIN_COLUMN;
import static domain.common.Constant.MIN_ROW;

import domain.place.piece.Side;
import java.util.List;
import java.util.Optional;

public class OutputView {

    private static final String INPUT_PLAYER_NAMES =
            "초, 한 순서대로 이름을 입력해주세요(예, pobi,quda)";

    private static final String HORSE_ELEPHANT_INPUT_FORMATION =
            "%s의 상차림을 입력해주세요.(예, 상마상마,마상마상, 상마마상, 마상상마)\n";

    private static final String INPUT_PIECE_MOVE =
            "%s(%s)가 이동할 기물을 선택해주세요. (예: 3,5)\n";

    private static final String INPUT_POSITION_MOVE =
            "%s(%s)가 이동할 위치를 입력해주세요. (예: 3,5)\n";

    private static final String PLAYER_CHECK =
            "%s가 장군을 당했습니다.\n";

    private static final String PLAYER_SCORE =
            "%s : %d점 \n %s : %d점\n";

    private static final String PLAYER_WINNER =
            "%s 승리!";

    private OutputView() {
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public static void printInputPlayerNames() {
        System.out.println(INPUT_PLAYER_NAMES);
    }

    public static void printHorseElephantFormation(Side side) {
        System.out.printf(HORSE_ELEPHANT_INPUT_FORMATION, side.getName());
    }

    public static void printPieceMove(String name, Side side) {
        System.out.printf(INPUT_PIECE_MOVE, name, side.getName());
    }

    public static void printPositionMove(String name, Side side) {
        System.out.printf(INPUT_POSITION_MOVE, name, side.getName());
    }

    public static void printCheck(Side side) {
        System.out.printf(PLAYER_CHECK, side.getName());
    }

    public static void printScore(Side side1, int score1, Side side2, int score2) {
        System.out.printf(PLAYER_SCORE, side1.getName(), score1, side2.getName(), score2);
    }

    public static void printWinner(Side side) {
        System.out.printf(PLAYER_WINNER, side.getName());
    }

    public static void printBoard(List<List<String>> boardFormats, List<List<Optional<Side>>> boardSides) {
        StringBuilder sb = new StringBuilder();
        appendHeader(sb);

        for (int row = 0; row < boardFormats.size(); row++) {
            appendRow(sb, row, boardFormats.get(row), boardSides.get(row));
        }

        System.out.println(sb);
    }

    private static void appendHeader(StringBuilder sb) {
        sb.append(" ");
        for (int i = MIN_COLUMN; i <= MAX_COLUMN; i++) {
            sb.append(String.format("%2d", i)).append(" ");
        }
        sb.append("\n");
    }

    private static void appendRow(StringBuilder sb, int rowNumber,
                                  List<String> rowFormats,
                                  List<Optional<Side>> rowSides) {
        sb.append(String.format("%2d ", rowNumber + MIN_ROW));

        for (int col = 0; col < rowFormats.size(); col++) {
            sb.append(colorize(rowFormats.get(col), rowSides.get(col))).append(" ");
        }

        sb.append("\n");
    }

    private static String colorize(String format, Optional<Side> side) {
        return side
                .map(s -> ColorMapper.colorize(format, s))
                .orElse(format);
    }
}
