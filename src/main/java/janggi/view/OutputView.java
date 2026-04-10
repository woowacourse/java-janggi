package janggi.view;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.unit.Empty;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";

    private static final String NODE = " ＋ ";
    private static final String PATH = " Ｏ ";
    private static final String H = "---";
    private static final String V = " ｜ ";
    private static final String BK = " \\ ";
    private static final String FW = " / ";
    private static final String SP = "   ";

    private static final List<String> X_LABELS =
            Arrays.stream(XPointFormat.values())
                    .map(XPointFormat::getFormat)
                    .toList();

    private static final List<String> Y_LABELS =
            List.of(" ０ ", " １ ", " ２ ", " ３ ", " ４ ", " ５ ", " ６ ", " ７ ", " ８ ");

    private static final List<List<Integer>> DIAG = List.of(
            List.of(9, 8, 1, -1),
            List.of(8, 7, -1, 1),
            List.of(2, 1, 1, -1),
            List.of(1, 0, -1, 1)
    );

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
        System.out.print(X_LABELS.get(x) + " ");
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
            return colored(hasPiece ? " " + piece.getName() + " " : PATH, BLUE);
        }
        if (hasPiece) {
            return colored(" " + piece.getName() + " ", Side.CHO.equals(piece.getSide()) ? GREEN : RED);
        }
        return NODE;
    }

    private void printConnectRow(int upperX) {
        int lowerX = upperX - 1;
        List<Integer> rule = getDiagRule(upperX, lowerX);

        System.out.print("   ");
        for (int y = 0; y <= 8; y++) {
            System.out.print(V);
            if (y < 8) {
                System.out.print(sepChar(rule, y));
            }
        }
        System.out.println();
    }

    private List<Integer> getDiagRule(int upperX, int lowerX) {
        return DIAG.stream()
                .filter(r -> r.get(0) == upperX && r.get(1) == lowerX)
                .findAny()
                .orElse(null);
    }

    private String sepChar(List<Integer> rule, int y) {
        if (rule == null) {
            return SP;
        }

        if (y == 3) {
            return rule.get(2) == 1 ? BK : FW;
        }
        if (y == 4) {
            return rule.get(3) == 1 ? BK : FW;
        }
        return SP;
    }

    private void printYLabels() {
        System.out.print("   ");
        for (int y = 0; y <= 8; y++) {
            System.out.print(Y_LABELS.get(y));
            if (y < 8) {
                System.out.print("   ");
            }
        }
        System.out.println();
    }

    public void printSide(Side side) {
        String color = Side.CHO.equals(side) ? GREEN : RED;
        System.out.println(colored(side.getName() + "차례입니다.", color));
    }

    public void printScore(Side side, double score) {
        String color = Side.CHO.equals(side) ? GREEN : RED;
        System.out.println(colored(side.getName() + " 점수 : " + score, color));
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
