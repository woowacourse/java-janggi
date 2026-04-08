package view;

import domain.Piece;
import domain.Team;
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
        System.out.print("  ");
        for (int col = 0; col <= MAX_COL; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();

        for (int row = MAX_ROW; row >= 0; row--) {
            System.out.print(row + " ");
            for (int col = 0; col <= MAX_COL; col++) {
                Position position = Position.of(row, col);
                if (board.containsKey(position)) {
                    Piece piece = board.get(position);

                    String color = getNationColor(piece);
                    String type = piece.getTypeName();
                    type = matchSoldierName(type, piece);

                    System.out.print(color + type + RESET_COLOR + " ");
                } else {
                    System.out.print("＋ ");
                }
            }
            System.out.println();
        }

        System.out.println();
    }

    public void printCurrentTurn(Team team) {
        if (team == Team.HAN) {
            System.out.println("현재는 한나라 차례입니다.");
        }
        if (team == Team.CHU) {
            System.out.println("현재는 초나라 차례입니다.");
        }
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

    public void printGameFinishMessage() {
        System.out.println("왕이 잡혀서 게임을 종료합니다.");
    }
}
