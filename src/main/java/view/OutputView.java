package view;

import domain.Piece;
import domain.vo.Position;

import java.util.Map;

public class OutputView {

    private static final String RESET_COLOR = "\u001B[0m";
    private static final String CHU_COLOR = "\u001B[32m";
    private static final String HAN_COLOR = "\u001B[31m";

    private static final String CHU_SOLDIER = "졸";
    private static final String HAN_SOLDIER = "병";
    private static final String CHU_NATION_NAME = "초";
    private static final String HAN_NATION_NAME = "한";

    private static final int MAX_COL = 8;
    private static final int MAX_ROW = 9;

    public void printBoard(Map<Position, Piece> board) {
        System.out.println();
        String[] rowLabels = {"일", "이", "삼", "사", "오", "육", "칠", "팔", "구", "십"};

        for (int row = MAX_ROW; row >= 0; row--) {
            System.out.print(rowLabels[row] + " ");
            for (int col = 0; col <= MAX_COL; col++) {
                Position position = Position.of(row, col);
                if (board.containsKey(position)) {
                    Piece piece = board.get(position);

                    String color = getNationColor(piece);
                    String type = piece.getTypeName();
                    type = matchSoldierName(type, piece);

                    System.out.print(color + type + RESET_COLOR + " ");
                } else {
                    System.out.print("ㅁ ");
                }
            }
            System.out.println();
        }
        System.out.println("  일 이 삼 사 오 육 칠 팔 구");
        System.out.println();
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
