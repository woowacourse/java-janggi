package view;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.piece.Piece;

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

        for (int row = JanggiCoordinate.BOUNDARY_START; row <= JanggiCoordinate.ROW_SIZE; row++) {
            for (int col = JanggiCoordinate.BOUNDARY_START; col <= JanggiCoordinate.COL_SIZE; col++) {
                JanggiCoordinate coordinate = new JanggiCoordinate(row, col);
                if (board.isOccupied(coordinate) && isCho(board, coordinate)) {
                    Piece piece = board.findPieceByCoordinate(coordinate);
                    System.out.print(GREEN + piece.getPieceType().getName() + RESET);
                    continue;
                }
                if (board.isOccupied(coordinate) && !isCho(board, coordinate)) {
                    Piece piece = board.findPieceByCoordinate(coordinate);
                    System.out.print(RED + piece.getPieceType().getName() + RESET);
                    continue;
                }
                System.out.print("＿");
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
