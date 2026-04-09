package janggi.view;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.domain.Score;
import janggi.domain.Team;
import janggi.dto.GameInfo;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static janggi.domain.piece.PieceType.*;

public class OutputView {

    private static final String ANSI_RED = "\u001B[1;31m";
    private static final String ANSI_BLUE = "\u001B[1;34m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static final int FIRST_ROW = Row.ROW_LOWER_THRESH_HOLD;
    private static final int LAST_ROW = Row.ROW_UPPER_THRESH_HOLD;
    private static final int FIRST_COL = Column.COLUMN_LOWER_THRESH_HOLD;
    private static final int LAST_COL = Column.COLUMN_UPPER_THRESH_HOLD;

    private static final String CROSS = " + ";
    private static final String H_LINE = "---";
    private static final String V_LINE = " | ";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Map<PieceType, String> PIECE_LABEL = Map.of(
            CHARIOT, "CHA", HORSE, "HOR", ELEPHANT, "ELE", GUARD, "GRD",
            GENERAL, "GEN", CANNON, "CAN", SOLDIER, "SOL"
    );

    public void printGameList(List<GameInfo> games) {
        System.out.println();
        System.out.println("========================= 게임 목록 ==========================");
        System.out.printf("%-6s %-19s %-6s %-6s %-6s %-6s%n", "ID", "마지막 플레이", "한 점수", "초 점수", "현재턴", "승자");
        System.out.println("------------------------------------------------------------");
        for (GameInfo game : games) {
            String result = game.getWinner() == Team.NONE ? "진행중" : toColorName(game.getWinner());
            System.out.printf("%-6d %-22s %-7.1f %-7.1f %-17s %-6s%n",
                    game.getId(),
                    game.getUpdatedAt().format(formatter),
                    game.getHanScore(),
                    game.getChoScore(),
                    game.getWinner() != Team.NONE ? ANSI_RED + "       " + ANSI_RESET : toColorName(game.getCurrentTeam()),
                    result);
        }
        System.out.println("------------------------------------------------------------");
    }

    public void printBoard(Map<Position, Piece> board, Score score) {
        System.out.println();
        printColumnHeader();
        for (int row = FIRST_ROW; row <= LAST_ROW; row++) {
            printPieceRow(board, row);
            if (row < LAST_ROW) {
                printLineBetweenRows(row);
            }
        }
        System.out.println();
        printLegendAndScore(score);
    }

    public void printWinner(Team winner) {
        String name = toColorName(winner);
        System.out.println(name + " 승리! Enter를 누르면 게임 목록 화면으로 돌아갑니다.");
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printGameQuit() {
        System.out.println("게임 목록 화면으로 돌아갑니다.");
    }

    public void printGameEnd() {
        System.out.println("게임을 종료합니다.");
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
            Piece piece = board.get(Position.of(row, col));
            System.out.print(formatPiece(piece));
            if (col < LAST_COL) System.out.print(H_LINE);
        }
        System.out.println();
    }

    private void printLineBetweenRows(int row) {
        System.out.print("   ");
        for (int col = FIRST_COL; col <= LAST_COL; col++) {
            System.out.print(V_LINE);
            if (col < LAST_COL) {
                System.out.print(getPalaceDiagonal(row, col));
            }
        }
        System.out.println();
    }

    private String getPalaceDiagonal(int row, int col) {
        if (isPalaceTopRow(row) && col == 4) return " ╲ ";
        if (isPalaceTopRow(row) && col == 5) return " ╱ ";
        if (isPalaceBottomRow(row) && col == 4) return " ╱ ";
        if (isPalaceBottomRow(row) && col == 5) return " ╲ ";
        return "   ";
    }

    private boolean isPalaceTopRow(int row) {
        return row == 1 || row == 8;
    }

    private boolean isPalaceBottomRow(int row) {
        return row == 2 || row == 9;
    }

    private String toRowLabel(int row) {
        return row == LAST_ROW ? "0" : String.valueOf(row);
    }

    private String formatPiece(Piece piece) {
        if (piece.isEmptyPiece()) return CROSS;
        if (piece.isSameTeam(Team.HAN)) {
            return ANSI_RED + PIECE_LABEL.get(piece.getType()) + ANSI_RESET;
        }
        return ANSI_BLUE + PIECE_LABEL.get(piece.getType()) + ANSI_RESET;
    }

    private void printLegendAndScore(Score score) {
        System.out.println(ANSI_RED + "  [HAN] CHA=車 HOR=馬 ELE=相 GRD=仕 GEN=將 CAN=包 SOL=兵 | " +
                "현재 점수: " + score.getHanScore() + ANSI_RESET);
        System.out.println(ANSI_BLUE + "  [CHO] CHA=車 HOR=馬 ELE=象 GRD=士 GEN=將 CAN=包 SOL=卒 | " +
                "현재 점수: " + score.getChoScore() + ANSI_RESET);
    }

    private String toColorName(Team team) {
        return team == Team.HAN
                ? ANSI_RED + "한(漢)" + ANSI_RESET
                : ANSI_BLUE + "초(楚)" + ANSI_RESET;
    }
}
