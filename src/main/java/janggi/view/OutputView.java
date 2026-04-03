package janggi.view;

import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.unit.Empty;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Set;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";

    private static final String NODE = "・";
    private static final String PATH = "〇";
    private static final String H = "－";
    private static final String V = "｜";
    private static final String BK = "＼";
    private static final String FW = "／";
    private static final String SP = "　";

    private static final String[] X_LABELS =
            {"가", "나", "다", "라", "마", "바", "사", "아", "자", "차"};
    private static final String[] Y_LABELS =
            {"０", "１", "２", "３", "４", "５", "６", "７", "８"};

    private static final int[][] DIAG = {
            {9, 8, 1, -1},
            {8, 7, -1, 1},
            {2, 1, 1, -1},
            {1, 0, -1, 1},
    };

    public void printBoard(Map<Point, Piece> board) {
        printBoardWithPath(board, null);
    }

    public void printBoardWithPath(Map<Point, Piece> board, Set<Point> destinations) {
        for (int x = 9; x >= 0; x--) {
            printPieceRow(x, board, destinations);
            if (x > 0) {
                printConnectRow(x);
            }
        }
        printYLabels();
    }

    private void printPieceRow(int x, Map<Point, Piece> board, Set<Point> destinations) {
        System.out.print(X_LABELS[x] + SP);
        for (int y = 0; y <= 8; y++) {
            System.out.print(nodeSymbol(x, y, board, destinations));
            if (y < 8) {
                System.out.print(H);
            }
        }
        System.out.println();
    }

    private String nodeSymbol(int x, int y, Map<Point, Piece> board, Set<Point> destinations) {
        Point p = Point.of(x, y);
        Piece piece = board.getOrDefault(p, Empty.INSTANCE);
        boolean hasPiece = !(piece instanceof Empty);

        if (destinations != null && destinations.contains(p)) {
            return colored(hasPiece ? piece.getName() : PATH, BLUE);
        }
        if (hasPiece) {
            return colored(piece.getName(), Side.CHO.equals(piece.getSide()) ? GREEN : RED);
        }
        return NODE;
    }

    private void printConnectRow(int upperX) {
        int lowerX = upperX - 1;
        int[] rule = getDiagRule(upperX, lowerX);

        System.out.print(SP + SP);
        for (int y = 0; y <= 8; y++) {
            System.out.print(colChar());
            if (y < 8) {
                System.out.print(sepChar(rule, y));
            }
        }
        System.out.println();
    }

    private int[] getDiagRule(int upperX, int lowerX) {
        for (int[] r : DIAG) {
            if (r[0] == upperX && r[1] == lowerX) {
                return r;
            }
        }
        return null;
    }

    private String colChar() {
        return V;
    }

    private String sepChar(int[] rule, int y) {
        if (rule == null) {
            return SP;
        }
        if (y == 3) {
            return rule[2] == 1 ? BK : FW;
        }
        if (y == 4) {
            return rule[3] == 1 ? BK : FW;
        }
        return SP;
    }

    private void printYLabels() {
        System.out.print(SP + SP);
        for (int y = 0; y <= 8; y++) {
            System.out.print(Y_LABELS[y]);
            if (y < 8) {
                System.out.print(SP);
            }
        }
        System.out.println();
    }

    public void printSide(Side side) {
        String color = Side.CHO.equals(side) ? GREEN : RED;
        System.out.println(colored(side.getName() + "차례입니다.", color));
    }

    public void printGameResult(Side side) {
        String color = Side.CHO.equals(side) ? GREEN : RED;
        System.out.println(colored(side.getName() + " 진영이 승리하였습니다.", color));
    }

    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private String colored(String text, String color) {
        return color + text + RESET;
    }
}
