package view;

import game.Board;
import piece.Country;
import piece.Piece;
import position.Column;
import position.Position;
import position.Row;

import java.util.Map;

public final class OutputView {
    private static final String BLUE = "\u001B[34m";  // 초나라 (파란색)
    private static final String RED = "\u001B[31m";   // 한나라 (빨간색)
    private static final String RESET = "\u001B[0m";

    public void displayBoard(Board board) {
        System.out.println();
        Map<Position, Piece> boardMap = board.getBoard();

        Row[] rows = Row.values();
        for (int i = rows.length - 1; i >= 0; i--) {
            displayRow(boardMap, rows[i]);
        }

        System.out.print("  ");
        for (Column column : Column.values()) {
            System.out.print(column.name());
        }
        System.out.println();
    }

    private void displayRow(Map<Position, Piece> boardMap, Row row) {
        System.out.print(formatRow(row) + " ");

        for (Column column : Column.values()) {
            Position position = new Position(column, row);
            displayPosition(boardMap, position);
        }

        System.out.println();
    }

    private String formatRow(Row row) {
        int rowIndex = row.ordinal() + 1;
        return rowIndex == 10 ? "0" : String.valueOf(rowIndex);
    }

    private void displayPosition(Map<Position, Piece> boardMap, Position position) {
        if (boardMap.containsKey(position)) {
            Piece piece = boardMap.get(position);
            displayPiece(piece);
        } else {
            System.out.print("ㅡ");
        }
    }

    private void displayPiece(Piece piece) {
        String color = piece.getCountry() == Country.Cho ? BLUE : RED;
        System.out.print(color + piece.getPieceType().getDisplayName() + RESET);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printTurn(Country country) {
        if (country == Country.Cho) {
            System.out.println("\n[초나라 턴입니다.]");
        } else {
            System.out.println("\n[한나라 턴입니다.]");
        }
    }
}
