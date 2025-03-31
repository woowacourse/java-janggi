package view;

import game.Board;
import java.util.Map;
import piece.Country;
import piece.Piece;
import position.Column;
import position.Position;
import position.Row;

public final class OutputView {
    private static final String BLUE = "\u001B[34m";  // 초나라 (파란색)
    private static final String RED = "\u001B[31m";   // 한나라 (빨간색)
    private static final String RESET = "\u001B[0m";

    public void displayBoard(Board board) {
        System.out.println();
        Map<Position, Piece> positionOfPiece = board.getBoard();

        Row[] rows = Row.values();
        for (int i = rows.length - 1; i >= 0; i--) {
            displayRow(positionOfPiece, rows[i]);
        }

        System.out.print("  ");
        for (Column column : Column.values()) {
            displayColumnName(column);
        }
        System.out.println();
    }

    private static void displayColumnName(final Column column) {
        String name = column.name();
        char ch = name.charAt(0);
        char fullWidthChar = (char) (ch - 0x20 + 0xFF00);
        System.out.print(fullWidthChar);
    }

    private void displayRow(Map<Position, Piece> positionOfPiece, Row row) {
        System.out.print(formatRow(row) + " ");

        for (Column column : Column.values()) {
            Position position = new Position(column, row);
            displayPosition(positionOfPiece, position);
        }

        System.out.println();
    }

    private String formatRow(Row row) {
        int rowIndex = row.ordinal() + 1;
        return rowIndex == 10 ? "0" : String.valueOf(rowIndex);
    }

    private void displayPosition(Map<Position, Piece> positionOfPiece, Position position) {
        if (positionOfPiece.containsKey(position)) {
            Piece piece = positionOfPiece.get(position);
            displayPiece(piece);
        } else {
            System.out.print("ㅡ");
        }
    }

    private void displayPiece(Piece piece) {
        String color = piece.getCountry() == Country.CHO ? BLUE : RED;
        System.out.print(color + piece.getPieceType().getDisplayName() + RESET);
    }


    public void displayTurnCountry(Country country) {
        if (country == Country.CHO) {
            System.out.println("\n[초나라 턴입니다.]");
        } else {
            System.out.println("\n[한나라 턴입니다.]");
        }
    }

    public void displayCountryScore(final Country turnCountry, final double countryScore) {
        System.out.println(turnCountry + " 점수 : " + countryScore);
    }

    public void displayGameFinished() {
        System.out.println("게임이 종료되었습니다.");
    }

    public void displayGameOver() {
        System.out.println("게임을 저장하고 종료하였습니다.");
    }

    public void displayError(final String message) {
        System.out.println("[ERROR] " + message);
    }
}
