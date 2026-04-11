package view;

import static common.Constant.MAX_COLUMN;
import static common.Constant.MIN_COLUMN;
import static common.Constant.MIN_ROW;

import domain.place.piece.Side;
import domain.player.Player;
import java.util.List;

public class OutputView {

    private static final String INPUT_GAME_ID=
            "게임 id를 입력해주세요(기존 게임을 원하시는 경우 실제 id를 입력해주시고, 새게임을 원하시면 0을 입력해주세요)";

    private static final String INPUT_PLAYER_NAMES =
            "초, 한 순서대로 이름을 입력해주세요(예, pobi,quda)";

    private static final String HORSE_ELEPHANT_INPUT_FORMATION =
            "%s의 상차림 번호 입력해주세요.(예시: 1)\n"
                    + "1. 상마상마\n"
                    + "2. 마상마상\n"
                    + "3. 상마마상\n"
                    + "4. 마상상마\n";

    private static final String INPUT_PIECE_MOVE =
            "%s(%s)가 이동할 기물을 선택해주세요.(예시: 1,1, 중단하려면 quit)\n";

    private static final String INPUT_POSITION_MOVE =
            "%s(%s)가 이동할 위치를 입력해주세요.(예시: 1,1, 중단하려면 quit)\n";

    private static final String PRINT_SCORE =
            "초나라 점수 : %.1f, 한나라 점수: %.1f\n";

    private static final String PRINT_WINNER =
            "승자 : %s %s\n";

    private static final String EMPTY = "．";

    private static final String[] NUMBERS = {
            "", "１", "２", "３", "４", "５", "６", "７", "８", "９", "１０"
    };

    private static final int CELL_WIDTH = 4;

    public void printMessage(String string) {
        System.out.println(string);
    }

    public void printGameId() {
        System.out.println(INPUT_GAME_ID);
    }

    public void printInputPlayerNames() {
        System.out.println(INPUT_PLAYER_NAMES);
    }

    public void printHorseElephantFormation(Side side) {
        System.out.printf(HORSE_ELEPHANT_INPUT_FORMATION, side.getName());
    }

    public void printPieceMove(String name, Side side) {
        System.out.printf(INPUT_PIECE_MOVE, name, side.getName());
    }

    public void printPositionMove(String name, Side side) {
        System.out.printf(INPUT_POSITION_MOVE, name, side.getName());
    }

    public void printScore(List<Double> scores) {
        System.out.printf(PRINT_SCORE, scores.get(0), scores.get(1));
    }

    public void printWinner(Player winner) {
        System.out.printf(PRINT_WINNER, winner.getSide().getName(), winner.getName());
    }

    public void printBoard(List<List<String>> boardFormats) {
        StringBuilder sb = new StringBuilder();

        sb.append("    ");
        for (int col = MIN_COLUMN; col <= MAX_COLUMN; col++) {
            sb.append(centerCell(NUMBERS[col]));
        }
        sb.append("\n");

        int rowNumber = MIN_ROW;
        for (List<String> row : boardFormats) {
            sb.append(formatRowLabel(NUMBERS[rowNumber]));

            for (String cell : row) {
                sb.append(centerCell(cell == null || cell.isBlank() ? EMPTY : cell));
            }

            sb.append("\n");
            rowNumber++;
        }

        System.out.print(sb + "\n");
    }

    private String formatRowLabel(String label) {
        int visibleWidth = getDisplayWidth(label);
        return " ".repeat(Math.max(0, 4 - visibleWidth)) + label;
    }

    private String centerCell(String text) {
        String plain = text.replaceAll("\u001B\\[[;\\d]*m", "");
        int visibleWidth = getDisplayWidth(plain);

        int left = (CELL_WIDTH - visibleWidth) / 2;
        int right = CELL_WIDTH - visibleWidth - left;

        return " ".repeat(Math.max(0, left))
                + text
                + " ".repeat(Math.max(0, right));
    }

    private int getDisplayWidth(String text) {
        int width = 0;

        for (char ch : text.toCharArray()) {
            if (ch >= '가' && ch <= '힣') {
                width += 2; // 한글
            } else if (ch >= 0xFF00 && ch <= 0xFFEF) {
                width += 2; // 전각 문자
            } else {
                width += 1;
            }
        }

        return width;
    }
}
