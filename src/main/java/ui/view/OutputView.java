package ui.view;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.piece.Piece;
import domain.player.Team;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OutputView {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";


    private static final String[] numberLabels = {"０", "１", "２", "３", "４", "５", "６", "７", "８", "９"};

    public void printBoard(Map<Position, Piece> board) {
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            printPieceRow(row, board, Set.of());
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

    public void printPlayerTurnMessage(String name, Team team) {
        System.out.println(name + "(" + team.name() + ")" + "님의 차례입니다.");
    }

    private void printPieceRow(int row, Map<Position, Piece> board, Set<Position> movablePositions) {
        StringBuilder sb = new StringBuilder();
        sb.append(numberLabels[row]).append(' ');
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Position position = new Position(row, column);
            Piece piece = board.get(position);
            sb.append(formatPiece(piece, movablePositions.contains(position)));
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

    private String formatPiece(Piece piece, boolean isMovablePosition) {
        if (piece.isNone()) {
            String noneDisplay = "＋";
            if (isMovablePosition) {
                return ANSI_BRIGHT_YELLOW + "〇" + ANSI_RESET;
            }
            return noneDisplay;
        }

        String displayName = piece.getPieceType().getDisplayName(piece.getTeam());
        if (isMovablePosition) {
            return ANSI_BRIGHT_YELLOW + displayName + ANSI_RESET;
        }
        Team team = piece.getTeam();

        if (team.isCho()) {
            return ANSI_BLUE + displayName + ANSI_RESET;
        }
        if (team.isHan()) {
            return ANSI_RED + displayName + ANSI_RESET;
        }
        return displayName;
    }

    public void printBoard(Map<Position, Piece> boardMap, Set<Position> movablePositions) {
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            printPieceRow(row, boardMap, movablePositions);
        }
        printColumnHeader();
        System.out.println();
    }

    public void printCaughtPieces(List<Piece> caughtPieces) {
        StringBuilder sb = new StringBuilder("잡힌 기물: ");
        if (caughtPieces.isEmpty()) {
            sb.append("없음");
            System.out.println(sb);
            return;
        }

        for (int index = 0; index < caughtPieces.size(); index++) {
            Piece piece = caughtPieces.get(index);
            sb.append(formatPiece(piece, false));
            if (index != caughtPieces.size() - 1) {
                sb.append(' ');
            }
        }
        System.out.println(sb);
    }
}
