package janggi.view;

import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Column;
import janggi.board.position.Position;
import janggi.board.position.Row;
import janggi.piece.Piece;

import java.util.Map;

public class OutputView {
    private static final String GREEN_COLOR_PREFIX = "\u001B[32m";
    private static final String GREEN_COLOR_SUFFIX = "\u001B[0m";
    private static final String RED_COLOR_PREFIX = "\u001B[31m";
    private static final String RED_COLOR_SUFFIX = "\u001B[0m";

    public void printGameStartMessage() {
        System.out.println("장기 게임을 시작합니다.");
        System.out.println("ex) 1,1 2,2 (1,1 에 위치한 기물을 2,2로 이동)\n");
    }

    public void printGameResult(Board board, Team winningTeam) {
        for (Team team : Team.values()) {
            int score = board.calculateScore(team);
            System.out.printf("%s의 승점 : %d\n", team.getDisplayName(), score);
        }
        System.out.printf("\n%s가 승리했습니다.(궁 잡음 혹은 승점 우승)\n", winningTeam.getDisplayName());
    }

    public void printBoard(Board currentBoard) {
        Map<Position, Piece> board = currentBoard.getBoard();
        System.out.println();
        for (Row row : Row.values()) {
            System.out.print(row.getValue() + " ");
            for (Column column : Column.values()) {
                printRowPieces(column, row, board);
            }
            System.out.println();
        }
        System.out.println("  0");
    }

    private void printRowPieces(Column column, Row row, Map<Position, Piece> board) {
        Position position = new Position(column, row);
        Piece piece = board.get(position);
        printPiece(piece);
    }

    private void printPiece(Piece piece) {
        if (piece == null) {
            System.out.print("＿ ");
            return;
        }
        if (piece.isSameTeam(Team.GREEN)) {
            System.out.print(GREEN_COLOR_PREFIX + piece.getDisplayName() + " " + GREEN_COLOR_SUFFIX);
            return;
        }
        System.out.print(RED_COLOR_PREFIX + piece.getDisplayName() + " " + RED_COLOR_SUFFIX);
    }

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }
}
