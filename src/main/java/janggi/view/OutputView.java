package janggi.view;

import janggi.Team;
import janggi.board.Board;
import janggi.board.Position;
import janggi.piece.Piece;

import java.util.Map;

public class OutputView {

    private static final String GREEN_COLOR_PREFIX = "\u001B[32m";
    private static final String GREEN_COLOR_SUFFIX = "\u001B[0m";
    private static final String RED_COLOR_PREFIX = "\u001B[31m";
    private static final String RED_COLOR_SUFFIX = "\u001B[0m";

    public void printGameStartMessage() {
        System.out.println("장기 게임을 시작합니다. 선공은 초나라입니다.");
        System.out.println("ex) 1,1 2,2 (1,1 에 위치한 기물을 2,2로 이동)\n");
    }

    public void printGameOver(Team winningTeam) {
        System.out.printf("%s가 승리했습니다.\n", winningTeam.getName());
    }

    public void printBoard(Board currentBoard) {
        Map<Position, Piece> board = currentBoard.getBoard();

        for (int row = Board.ROW_SIZE - 1; row > -1 ; row--) {
            System.out.print(row + " ");
            for (int column = 0; column < Board.COLUMN_SIZE; column++) {
                printRowPieces(column, row, board);
            }
            System.out.println();
        }
    }

    private void printRowPieces(int column, int row, Map<Position, Piece> board) {
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
            System.out.print(GREEN_COLOR_PREFIX + piece + " " + GREEN_COLOR_SUFFIX);
            return;
        }
        System.out.print(RED_COLOR_PREFIX + piece + " " + RED_COLOR_SUFFIX);
    }
}
