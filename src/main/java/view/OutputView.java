package view;

import domain.board.Side;
import domain.piece.PieceType;
import dto.BoardDto;

import dto.PieceDto;
import dto.PositionDto;
import view.message.PieceFormatter;
import view.message.SideView;

public class OutputView {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;

    public void printBoard(BoardDto boardDto) {
        System.out.println("\n   0  1   2  3   4   5  6   7  8");

        for (int i = 0; i < COL_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < ROW_SIZE; j++) {
                printPieceBySide(boardDto.board().get(new PositionDto(i, j)));
            }

            System.out.println();
        }
    }

    private void printPieceBySide(PieceDto pieceDto) {
        Side side = pieceDto.getSide();

        if (pieceDto.getPieceType() == PieceType.EMPTY) {
            System.out.print(" " + PieceFormatter.from(pieceDto.getPieceType()) + " ");
            return;
        }

        System.out.print(" " + SideView.getSideColor(side) + PieceFormatter.from(pieceDto.getPieceType()) + SideView.getResetColor() + " ");
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }
}
