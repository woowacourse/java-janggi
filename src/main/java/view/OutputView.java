package view;

import board.GameBoard;
import direction.Point;
import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.General;
import piece.Guard;
import piece.Horse;
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
            System.out.print(pieceToString(findPiece));
            return;
        }

        System.out.print(".");
    }

    private static String pieceToString(Piece findPiece) {
        if (findPiece.isSameType(new Chariot())) {
            return "c";
        }

        if (findPiece.isSameType(new Cannon())) {
            return "n";
        }

        if (findPiece.isSameType(new Horse())) {
            return "h";
        }

        if (findPiece.isSameType(new Elephant())) {
            return "e";
        }

        if (findPiece.isSameType(new Guard())) {
            return "u";
        }

        if (findPiece.isSameType(new General())) {
            return "g";
        }

        return "s";
    }

    public static void displayWrongPoint() {
        System.out.println("본인의 기물이 아닙니다. 다시 선택해 주세요.");
    }
}
