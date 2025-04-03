package view;

import direction.Point;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import team.Team;

public class OutputView {

    private static final int ROW_START = 1;
    private static final int ROW_END = 10;
    private static final int COLUMN_START = 1;
    private static final int COLUMN_END = 9;

    public static void printNowTurn(Team turn) {
        System.out.printf("%s 턴 입니다.%n", teamToKorean(turn));
    }

    private static String teamToKorean(Team team) {
        if (team.equals(Team.HAN)) {
            return "한나라";
        }

        return "초나라";
    }

    public static void printBoard(final Pieces pieces) {
        System.out.println();
        for (Team team : Team.values()) {
            System.out.printf("%s: %f점 ", teamToKorean(team), pieces.calculateScore(team));
        }
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
        if (pieces.isExistPieceInPoint(point)) {
            Piece findPiece = pieces.findByPoint(point);
            System.out.print(pieceToString(findPiece));
            return;
        }

        System.out.print(".");
    }

    public static void displayWrongPoint() {
        System.out.println("본인의 기물이 아닙니다. 다시 선택해 주세요.");
    }

    private static String pieceToString(Piece findPiece) {
        if (findPiece.isSameType(PieceType.CHARIOT)) {
            return "c";
        }

        if (findPiece.isSameType(PieceType.CANNON)) {
            return "n";
        }

        if (findPiece.isSameType(PieceType.HORSE)) {
            return "h";
        }

        if (findPiece.isSameType(PieceType.ELEPHANT)) {
            return "e";
        }

        if (findPiece.isSameType(PieceType.GUARD)) {
            return "u";
        }

        if (findPiece.isSameType(PieceType.GENERAL)) {
            return "g";
        }

        return "s";
    }
}
