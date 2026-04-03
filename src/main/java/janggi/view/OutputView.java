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

    // 전각(EAW=W/F, 폭2) 문자만 사용 → Mac/Windows 열 정렬 보장
    private static final String NODE = "・";  // 빈 교점
    private static final String PATH = "○";  // 이동 가능
    private static final String H = "－";  // 가로선
    private static final String V = "｜";  // 세로선
    private static final String BK = "＼";  // 대각 ╲
    private static final String FW = "／";  // 대각 ╱
    private static final String CX = "Ｘ";  // 궁성 중심 / 세로 연결행 교차
    private static final String SP = "　";  // 전각 공백

    // 초(CHO) 궁성: x=0~2, y=3~5, 중심=(1,4)
    // 한(HAN) 궁성: x=7~9, y=3~5, 중심=(8,4)
    private static final int PAL_Y1 = 3, PAL_Y2 = 5;
    private static final int CHO_X1 = 0, CHO_X2 = 2, CHO_XC = 1, CHO_YC = 4;
    private static final int HAN_X1 = 7, HAN_X2 = 9, HAN_XC = 8, HAN_YC = 4;

    private static final String[] X_LABELS =
            {"가", "나", "다", "라", "마", "바", "사", "아", "자", "차"};
    private static final String[] Y_LABELS =
            {"０", "１", "２", "３", "４", "５", "６", "７", "８"};

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

    // 기물행: [레이블+SP] [node] [H] [node] [H] ... [node]
    // 슬롯: 전각2 + 전각2 = 4 고정
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
        // 궁성 중심: 기물 없을 때만 Ｘ
        if (isPalCenter(x, y)) {
            return CX;
        }
        return NODE;
    }

    // 세로 연결행: [SP+SP] [v] [SP] [v] [SP] ... [v]
    // 슬롯: 전각2 + 전각2 = 4 (기물행과 동일 → 교점/세로선 열 정렬)
    private void printConnectRow(int upperX) {
        int lowerX = upperX - 1;
        System.out.print(SP + SP);
        for (int y = 0; y <= 8; y++) {
            System.out.print(vChar(upperX, lowerX, y));
            if (y < 8) {
                System.out.print(SP);
            }
        }
        System.out.println();
    }

    // ╲: upperX의 y → lowerX의 y+1 (화면 왼위→오른아래)
    // ╱: upperX의 y → lowerX의 y-1 (화면 오른위→왼아래)
    private String vChar(int upperX, int lowerX, int y) {
        boolean hasBK = isPalSeg(upperX, y, lowerX, y + 1);
        boolean hasFW = isPalSeg(upperX, y, lowerX, y - 1);
        if (hasBK && hasFW) {
            return CX;
        }
        if (hasBK) {
            return BK;
        }
        if (hasFW) {
            return FW;
        }
        return V;
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

    // (x1,y1)→(x2,y2) 가 궁성 대각선(중심↔꼭짓점) 위의 1칸 구간인지
    private boolean isPalSeg(int x1, int y1, int x2, int y2) {
        int[][] segs = {
                {CHO_XC, CHO_YC, CHO_X1, PAL_Y1}, {CHO_XC, CHO_YC, CHO_X1, PAL_Y2},
                {CHO_XC, CHO_YC, CHO_X2, PAL_Y1}, {CHO_XC, CHO_YC, CHO_X2, PAL_Y2},
                {HAN_XC, HAN_YC, HAN_X1, PAL_Y1}, {HAN_XC, HAN_YC, HAN_X1, PAL_Y2},
                {HAN_XC, HAN_YC, HAN_X2, PAL_Y1}, {HAN_XC, HAN_YC, HAN_X2, PAL_Y2},
        };
        for (int[] s : segs) {
            if (onDiag(x1, y1, x2, y2, s[0], s[1], s[2], s[3])) {
                return true;
            }
        }
        return false;
    }

    private boolean onDiag(int x1, int y1, int x2, int y2,
                           int ax, int ay, int bx, int by) {
        int dx = Integer.signum(bx - ax), dy = Integer.signum(by - ay);
        int steps = Math.max(Math.abs(bx - ax), Math.abs(by - ay));
        for (int i = 0; i < steps; i++) {
            int cx = ax + dx * i, cy = ay + dy * i;
            int nx = ax + dx * (i + 1), ny = ay + dy * (i + 1);
            if (cx == x1 && cy == y1 && nx == x2 && ny == y2) {
                return true;
            }
            if (cx == x2 && cy == y2 && nx == x1 && ny == y1) {
                return true;
            }
        }
        return false;
    }

    private boolean isPalCenter(int x, int y) {
        return (x == CHO_XC && y == CHO_YC) || (x == HAN_XC && y == HAN_YC);
    }

    public void printSide(Side side) {
        String color = Side.CHO.equals(side) ? GREEN : RED;
        System.out.println(color + side.getName() + " 차례입니다." + RESET);
    }

    public void printGameResult(Side side) {
        System.out.println(side.getName() + " 진영이 승리하였습니다.");
    }

    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private String colored(String text, String color) {
        return color + text + RESET;
    }
}
