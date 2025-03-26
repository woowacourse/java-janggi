package view;

import board.GameBoard;
import direction.Point;
import piece.Piece;
import piece.Pieces;

public class OutputView {

    private static final int ROW_START = 1;
    private static final int ROW_END = 10;
    private static final int COLUMN_START = 1;
    private static final int COLUMN_END = 9;

    public static void printBoard(final GameBoard gameBoard) {
        Pieces pieces = gameBoard.findAllPieces();

        System.out.println();
        printPiecesInBoard(pieces);
        System.out.println("123456789");
        System.out.println();
    }

    private static void printPiecesInBoard(Pieces pieces) {
        for (int row = ROW_START; row <= ROW_END; row++) {
            printPiecesInRow(row, pieces);
            System.out.println(" " + row);
        }
    }

    private static void printPiecesInRow(int row, Pieces pieces) {
        for (int column = COLUMN_START; column <= COLUMN_END; column++) {
            Point point = new Point(column, row);
            printPieceInPosition(pieces, point);
        }
    }

    private static void printPieceInPosition(final Pieces pieces, final Point point) {
        if (pieces.isExistPieceIn(point)) {
            Piece findPiece = pieces.findByPoint(point);
            System.out.print(findPiece.getNickname());
            return;
        }

        System.out.print(".");
    }

    public static void displayWrongPoint() {
        System.out.println("본인의 기물이 아닙니다. 다시 선택해 주세요.");
    }
}
