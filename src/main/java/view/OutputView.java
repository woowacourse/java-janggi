package view;

import domain.coordinate.Position;
import domain.Side;
import domain.piece.Piece;
import dto.BoardDto;
import view.message.PieceView;

import view.message.SideView;

public class OutputView {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;

    public void printBoard(BoardDto boardDto) {
        System.out.println("   0  1   2  3   4   5  6   7  8");

        for (int i = 0; i < COL_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < ROW_SIZE; j++) {
                printPieceBySide(boardDto.getBoard().get(new Position(i, j)));
            }
            System.out.println();
        }
    }

    private void printPieceBySide(Piece piece) {
        Side side = piece.getSide();

        if (piece.isNeutral()) {
            System.out.print(" " + PieceView.from(piece) + " ");
            return;
        }
        System.out.print(" " + SideView.getSideColor(side) + PieceView.from(piece) + SideView.getResetColor() + " ");
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }
}
