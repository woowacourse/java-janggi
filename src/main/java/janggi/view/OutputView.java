package janggi.view;

import janggi.domain.board.point.Point;
import janggi.domain.piece.unit.Empty;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Set;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private static final String EMPTY_SYMBOL = "＋";
    private static final String PATH_SYMBOL = "〇";

    public void printBoard(Map<Point, Piece> board) {
        printBoardWithPath(board, null);
    }

    public void printBoardWithPath(Map<Point, Piece> board, Set<Point> destinations) {
        String[] xLabels = {"가", "나", "다", "라", "마", "바", "사", "아", "자", "차"};
        String[] yLabels = {"０", "１", "２", "３", "４", "５", "６", "７", "８"};

        for (int x = 9; x >= 0; x--) {
            System.out.print(cell(xLabels[x]));

            for (int y = 0; y <= 8; y++) {
                Point point = Point.of(x, y);
                Piece piece = board.getOrDefault(point, Empty.INSTANCE);

                if (destinations != null && destinations.contains(point)) {
                    String symbol = isEmpty(piece) ? PATH_SYMBOL : piece.getName();
                    System.out.print(colored(cell(symbol), ANSI_BLUE));
                    continue;
                }

                if (isEmpty(piece)) {
                    System.out.print(cell(EMPTY_SYMBOL));
                    continue;
                }

                String color = Side.CHO.equals(piece.getSide()) ? ANSI_GREEN : ANSI_RED;
                System.out.print(colored(cell(piece.getName()), color));
            }
            System.out.println();
        }

        System.out.print(cell("　")); // 전각 공백
        for (String label : yLabels) {
            System.out.print(cell(label));
        }
        System.out.println();
    }

    public void printSide(Side side) {
        String color = Side.CHO.equals(side) ? ANSI_GREEN : ANSI_RED;
        System.out.println(color + side.getName() + " 차례입니다." + ANSI_RESET);
    }

    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private boolean isEmpty(Piece piece) {
        return piece instanceof Empty;
    }

    // 전각 문자는 모두 2컬럼이므로 뒤에 공백 1개만 붙이면 정렬됨
    private String cell(String value) {
        return value + " ";
    }

    private String colored(String text, String color) {
        return color + text + ANSI_RESET;
    }
}
