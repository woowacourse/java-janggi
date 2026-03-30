package view;

import domain.coordinate.Position;
import domain.Side;
import domain.piece.Piece;
import view.message.PieceView;

import java.util.List;
import view.message.SideView;

public class OutputView {


    public void printBoard(Piece[][] board) {
        System.out.println("   0  1   2  3   4   5  6   7  8");

        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 9; j++) {
                printPieceBySide(board[i][j]);
            }
            System.out.println();
        }
    }

    public void printAvailablePositions(List<Position> possibleMoves) {
        int index = 1;
        for (Position possibleMove : possibleMoves) {
            System.out.printf("%d. (%d, %d)\n", index++, possibleMove.col(), possibleMove.row());
        }
    }

    private void printPieceBySide(Piece piece) {
        Side side = piece.getSide();

        if (piece.isEmpty()) {
            System.out.print(" " + PieceView.from(piece) + " ");
            return;
        }
        System.out.print(" " + SideView.getSideColor(side) + PieceView.from(piece) + SideView.getResetColor() + " ");
    }

    public void printCanNotMovablePieceError() {
        System.out.println("\n해당 기물은 움직일 수 있는 좌표가 없습니다. 다른 기물을 선택해주세요.\n");
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }
}
