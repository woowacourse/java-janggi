package view;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.piece.Piece;

import static domain.JanggiBoard.COL_SIZE;
import static domain.JanggiBoard.ROW_SIZE;

public class OutputView {
    public static final String RESET = "\u001B[0m";  // 색상 초기화
    public static final String RED = "\u001B[31m";   // 빨간색
    public static final String GREEN = "\u001B[32m"; // 초록색

    private static final int PRINT_START_ROW = 0;
    private static final int PRINT_START_COL = 0;

    public void printCurrTurn(Country currTurn) {
        System.out.println("현재 " + currTurn.getName() + "의 차례입니다.");
    }

    public void printCurrBoard(JanggiBoard board) {

        for (int row = PRINT_START_ROW; row <= ROW_SIZE; row++) {
            if (row == PRINT_START_ROW) {
                System.out.println("  １ ２ ３ ４ ５ ６ ７ ８ ９");
                continue;
            }

            for (int col = PRINT_START_COL; col <= COL_SIZE; col++) {
                if (col == PRINT_START_COL) {
                    if (row == 10) {
                        System.out.print(0 + " ");
                        continue;
                    }
                    System.out.print(row + " ");
                    continue;
                }

                JanggiCoordinate coordinate = new JanggiCoordinate(row, col);
                if (board.isOccupied(coordinate) && isCho(board, coordinate)) {
                    Piece piece = board.findPieceByCoordinate(coordinate);
                    System.out.print(GREEN + piece.getPieceType().getName() + " " + RESET);
                    continue;
                }
                if (board.isOccupied(coordinate) && !isCho(board, coordinate)) {
                    Piece piece = board.findPieceByCoordinate(coordinate);
                    System.out.print(RED + piece.getPieceType().getName() + " " + RESET);
                    continue;
                }
                System.out.print("＿ ");
            }
            System.out.println();
        }
    }

    private boolean isCho(JanggiBoard board, JanggiCoordinate coordinate) {
        return board.findPieceByCoordinate(coordinate).getCountry() == Country.CHO;
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
