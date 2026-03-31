package janggi.view;

import janggi.domain.piece.Piece;
import janggi.domain.Team;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

import java.util.Map;

public class OutputView {

    private static final String ANSI_RED   = "\u001B[1;31m";
    private static final String ANSI_BLUE  = "\u001B[1;34m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static final int FIRST_ROW = Row.ROW_LOWER_THRESH_HOLD;
    private static final int LAST_ROW  = Row.ROW_UPPER_THRESH_HOLD;
    private static final int FIRST_COL = Column.COLUMN_LOWER_THRESH_HOLD;
    private static final int LAST_COL  = Column.COLUMN_UPPER_THRESH_HOLD;

    private static final String CROSS  = " + ";
    private static final String H_LINE = "---";
    private static final String V_LINE = " | ";

    private static final Map<String, String> PIECE_LABEL = Map.of(
            "차", "CHA", "마", "HOR", "상", "ELE", "사", "GRD",
            "장", "GEN", "포", "CAN", "졸", "SOL"
    );

    public void printBoard(Map<Position, Piece> board) {
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

    private void printColumnHeader() {
        System.out.print("   ");
        for (int col = FIRST_COL; col <= LAST_COL; col++) {
            System.out.printf(" %d ", col);
            if (col < LAST_COL) System.out.print("   ");
        }
        System.out.println();
    }

    private void printPieceRow(Map<Position, Piece> board, int row) {
        System.out.print(" " + toRowLabel(row) + " ");
        for (int col = FIRST_COL; col <= LAST_COL; col++) {
            Piece piece = board.get(createPosition(row, col));
            System.out.print(formatPiece(piece));
            if (col < LAST_COL) System.out.print(H_LINE);
        }
        System.out.println();
    }

    private void printLineBetweenRows() {
        System.out.print("   ");
        for (int col = FIRST_COL; col <= LAST_COL; col++) {
            System.out.print(V_LINE);
            if (col < LAST_COL) System.out.print("   ");
        }
        System.out.println();
    }

    private String toRowLabel(int row) {
        return row == LAST_ROW ? "0" : String.valueOf(row);
    }

    private Position createPosition(int row, int col) {
        if (row == LAST_ROW) return Position.from("0" + col);
        return Position.from("" + row + col);
    }

    private String formatPiece(Piece piece) {
        if (piece.isEmptyPiece()) return CROSS;
        if (piece.isSameTeam(Team.HAN)) {
            return ANSI_RED + PIECE_LABEL.get(piece.getDisplayName()) + ANSI_RESET;
        }
        return ANSI_BLUE + PIECE_LABEL.get(piece.getDisplayName()) + ANSI_RESET;
    }

    private void printLegend() {
        System.out.println(ANSI_RED + "  [HAN] CHA=車 HOR=馬 ELE=相 GRD=仕 GEN=將 CAN=包 SOL=兵" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "  [CHO] CHA=車 HOR=馬 ELE=象 GRD=士 GEN=將 CAN=包 SOL=卒" + ANSI_RESET);
    }

    public void printWinner(Team winner) {
        String name = winner == Team.HAN
                ? ANSI_RED + "한(漢)" + ANSI_RESET
                : ANSI_BLUE + "초(楚)" + ANSI_RESET;
        System.out.println(name + " 승리! 게임을 종료합니다.");
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printGameEnd() {
        System.out.println("게임을 종료합니다.");
    }
}
