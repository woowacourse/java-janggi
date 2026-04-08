package view;

import domain.board.Piece;
import domain.game.Status;
import domain.vo.Position;

import java.util.Map;

public class OutputView {

    private static final String RESET_COLOR = "\u001B[0m";
    private static final String CHU_COLOR = "\u001B[32m";
    private static final String HAN_COLOR = "\u001B[31m";
    private static final String PALACE_COLOR = "\u001B[100m";

    private static final String CHU_SOLDIER = "졸";
    private static final String HAN_SOLDIER = "병";
    private static final String CHU_NATION_NAME = "초";
    private static final String HAN_NATION_NAME = "한";

    private static final int MAX_COL = 8;
    private static final int MAX_ROW = 9;

    public void printBoard(Map<Position, Piece> pieces) {
        System.out.println();
        printRows(pieces);
        System.out.println("  영 일 이 삼 사 오 육 칠 팔");
        System.out.println();
    }

    public void printScore(int chuScore, int hanScore) {
        System.out.println("게임 점수 : ");
        System.out.println("초나라: " + chuScore + "점");
        System.out.println("한나라: " + hanScore + "점");
        System.out.println();
    }

    public void printGameResult(Status status) {
        System.out.println("게임 종료 : ");
        if (status == Status.CHU_WIN) {
            System.out.println("초나라가 승리했습니다!");
            System.out.println();
            return;
        }
        if (status == Status.HAN_WIN) {
            System.out.println("한나라가 승리했습니다!");
            System.out.println();
            return;
        }
        System.out.println("잠시 쉬도록 하겠습니다.");
        System.out.println();
    }

    public void printMessage(String message) {
        System.out.println(message);
        System.out.println();
    }

    private void printRows(Map<Position, Piece> pieces) {
        String[] rowLabels = {"영", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};

        for (int row = MAX_ROW; row >= 0; row--) {
            System.out.print(rowLabels[row] + " ");
            for (int col = 0; col <= MAX_COL; col++) {
                printCell(pieces, Position.of(row, col), row, col);
            }
            System.out.println();
        }
    }

    private void printCell(Map<Position, Piece> pieces, Position position, int row, int col) {
        String content = getContent(pieces, position);
        if (isInPalace(row, col)) {
            System.out.print(PALACE_COLOR + content + RESET_COLOR);
        }
        else
            System.out.print(content + RESET_COLOR);
    }

    private String getContent(Map<Position, Piece> pieces, Position position) {
        if (!pieces.containsKey(position)) {
            return "ㅁ ";
        }

        Piece piece = pieces.get(position);
        String color = getNationColor(piece);
        String type = piece.getTypeName();
        type = matchSoldierName(type, piece);

        return color + type + " ";
    }

    private boolean isInPalace(int row, int col) {
        boolean isChuPalace = (row >= 0 && row <= 2 && col >= 3 && col <= 5);
        boolean isHanPalace = (row >= 7 && row <= 9 && col >= 3 && col <= 5);

        return isChuPalace || isHanPalace;
    }

    private static String getNationColor(Piece piece) {
        if(piece.getTeamName().equals(CHU_NATION_NAME))
            return CHU_COLOR;
        return HAN_COLOR;
    }

    private String matchSoldierName(String type, Piece piece) {
        if (type.equals(CHU_SOLDIER) && piece.getTeamName().equals(HAN_NATION_NAME)) {
            type = HAN_SOLDIER;
        }
        return type;
    }
}
