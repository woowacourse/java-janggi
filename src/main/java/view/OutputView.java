package view;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MIN_COLUMN;
import static domain.common.Constant.MIN_ROW;

import domain.place.piece.Side;
import java.util.List;

public class OutputView {

    private static final String INPUT_PLAYER_NAMES =
            "초, 한 순서대로 이름을 입력해주세요(예, pobi,quda)";

    private static final String HORSE_ELEPHANT_INPUT_FORMATION =
            "%s의 상차림을 입력해주세요.(예, 상마상마,마상마상, 상마마상, 마상상마)\n";

    private static final String INPUT_PIECE_MOVE =
            "%s(%s)가 이동할 기물을 선택해주세요.\n";

    private static final String INPUT_POSITION_MOVE =
            "%s(%s)가 이동할 위치를 입력해주세요.\n";

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

    public static void printBoard(List<List<String>> boardFormats) {
        StringBuilder sb = new StringBuilder();

        sb.append(" ");
        for (int i = MIN_COLUMN; i <= MAX_COLUMN; i++) {
            sb.append(String.format("%2d", i)).append(" ");
        }
        sb.append("\n");

        int rowNumber = MIN_ROW;
        for (List<String> row : boardFormats) {
            sb.append(String.format("%2d ", rowNumber));
            for (String format : row) {
                sb.append(format).append(" ");
            }
            sb.append("\n");
            rowNumber++;
        }

        System.out.println(sb);
    }
}