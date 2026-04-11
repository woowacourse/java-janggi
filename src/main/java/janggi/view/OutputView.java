package janggi.view;

import janggi.domain.Score;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import java.util.Map;

public class OutputView {

    private static final String ANSI_RED   = "\u001B[1;31m";
    private static final String ANSI_BLUE  = "\u001B[1;34m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static final int FIRST_ROW = 1;
    private static final int LAST_ROW  = 10;
    private static final int FIRST_COL = Column.COLUMN_LOWER_THRESH_HOLD;
    private static final int LAST_COL  = Column.COLUMN_UPPER_THRESH_HOLD;

    private static final String CROSS  = " + ";
    private static final String H_LINE = "---";
    private static final String V_LINE = " | ";

    private static final Map<PieceType, String> PIECE_LABELS = Map.of(
            PieceType.CHARIOT,  "CHA",
            PieceType.HORSE,    "HOR",
            PieceType.ELEPHANT, "ELE",
            PieceType.GUARD,    "GRD",
            PieceType.GENERAL,  "GEN",
            PieceType.CANNON,   "CAN",
            PieceType.SOLDIER,  "SOL"
    );

    private OutputView() {
    }

    public static void printBoard(Map<Position, Piece> board) {
        System.out.println();
        printColumnHeader();
        for (int row = FIRST_ROW; row <= LAST_ROW; row++) {
            printPieceRow(board, row);
            if (row < LAST_ROW) {
                printLineBetweenRows();
            }
        }
        System.out.println();
        printLegend();
    }

    private static void printColumnHeader() {
        System.out.print("    ");
        for (int col = FIRST_COL; col <= LAST_COL; col++) {
            System.out.printf(" %d ", col);
            if (col < LAST_COL) System.out.print("   ");
        }
        System.out.println();
    }

    private static void printPieceRow(Map<Position, Piece> board, int row) {
        System.out.print(" " + toRowLabel(row) + " ");
        for (int col = FIRST_COL; col <= LAST_COL; col++) {
            Piece piece = board.get(Position.of(row, col));
            System.out.print(formatPiece(piece));
            if (col < LAST_COL) System.out.print(H_LINE);
        }
        System.out.println();
    }

    private static void printLineBetweenRows() {
        System.out.print("   ");
        for (int col = FIRST_COL; col <= LAST_COL; col++) {
            System.out.print(V_LINE);
            if (col < LAST_COL) System.out.print("   ");
        }
        System.out.println();
    }

    private static String toRowLabel(int row) {
        if (row == LAST_ROW) {
            return "0";
        }
        return String.valueOf(row);
    }

    private static String formatPiece(Piece piece) {
        if (piece.isEmptyPiece()) return CROSS;
        if (piece.isSame(Team.HAN)) {
            return ANSI_RED + PIECE_LABELS.get(piece.getType()) + ANSI_RESET;
        }
        return ANSI_BLUE + PIECE_LABELS.get(piece.getType()) + ANSI_RESET;
    }

    private static void printLegend() {
        System.out.println(ANSI_RED + "  [HAN] CHA=車 HOR=馬 ELE=相 GRD=仕 GEN=將 CAN=包 SOL=兵" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "  [CHO] CHA=車 HOR=馬 ELE=象 GRD=士 GEN=將 CAN=包 SOL=卒" + ANSI_RESET);
    }

    public static void printScore(Score hanScore, Score choScore) {
        System.out.printf("한(漢) 점수: %.1f%n", hanScore.getValue());
        System.out.printf("초(楚) 점수: %.1f%n", choScore.getValue());
    }

    public static void printScoreWinner(Team winner) {
        if (winner == Team.HAN) {
            System.out.println(ANSI_RED + "한(漢)" + ANSI_RESET + " 점수 우세!");
            return;
        }
        System.out.println(ANSI_BLUE + "초(楚)" + ANSI_RESET + " 점수 우세!");
    }

    public static void printDraw() {
        System.out.println("점수가 동일합니다!");
    }

    public static void printWinner(Team winner) {
        if (winner == Team.HAN) {
            System.out.println(ANSI_RED + "한(漢)" + ANSI_RESET + " 승리! 게임을 종료합니다.");
            return;
        }
        System.out.println(ANSI_BLUE + "초(楚)" + ANSI_RESET + " 승리! 게임을 종료합니다.");
    }

    public static void printError(String message) {
        System.out.println(message);
    }

    public static void printGameEnd() {
        System.out.println("게임을 종료합니다.");
    }

    public static void printGameId(long gameId) {
        System.out.println("게임 번호: " + gameId + " (이어하기 시 필요합니다.)");
    }
}
