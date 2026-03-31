package view;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.piece.Piece;
import domain.player.Team;
import domain.position.Position;
import java.util.Map;

public class OutputView {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private static final String[] numberLabels = {"０", "１", "２", "３", "４", "５", "６", "７", "８", "９"};

    public void printBoard(Map<Position, Piece> board) {
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            printPieceRow(row, board);
        }
        printColumnHeader();
        System.out.println();
    }

    public void printWinner(Team winner) {
        System.out.println("===== 게임 종료 =====");
        if (winner.isCho()) {
            System.out.println("초 팀이 이겼습니다.");
        }
        if (winner.isHan()) {
            System.out.println("한 팀이 이겼습니다.");
        }
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printPlayerTurnMessage(String name, String team) {
        System.out.println(name + "(" + team + ")" + "님의 차례입니다.");
    }

    private void printPieceRow(int row, Map<Position, Piece> board) {
        StringBuilder sb = new StringBuilder();
        sb.append(numberLabels[row]).append(' ');
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Piece piece = board.get(new Position(row, column));
            sb.append(formatPiece(piece));
            if (column != MAX_COLUMN) {
                sb.append(' ');
            }
        }
        System.out.println(sb);
    }

    private void printColumnHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("　 ");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append(numberLabels[column]);
            if (column != MAX_COLUMN) {
                sb.append(' ');
            }
        }
        System.out.println(sb);
    }

    private String formatPiece(Piece piece) {
        if (piece.isNone()) {
            return "＋";
        }

        String displayName = piece.getPieceType().getDisplayName(piece.getTeam());
        Team team = piece.getTeam();

        if (team.isCho()) {
            return ANSI_BLUE + displayName + ANSI_RESET;
        }
        if (team.isHan()) {
            return ANSI_RED + displayName + ANSI_RESET;
        }
        return displayName;
    }
}
