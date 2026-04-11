package view;

import domain.Board;
import domain.Position;
import domain.Team;
import domain.piece.Piece;

public class OutputView {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    public static void printGameOver() {
        System.out.println("GAME OVER");
    }

    public static void printBoard(Board board) {
        System.out.println();
        System.out.println("==== 장기판 =====");

        System.out.print("   ");
        for (int col = 1; col <= 9; col++) {
            System.out.printf(" %d ", col);
        }
        System.out.println();

        for (int row = 1; row <= 10; row++) {
            System.out.printf("%2d ", row);
            for (int col = 1; col <= 9; col++) {
                Position position = Position.from(row, col);

                if (board.isEmpty(position)) {
                    System.out.print(" . ");
                } else {
                    Piece piece = board.findPiece(position);

                    String color = getColor(piece);
                    System.out.print(color + " " + piece.getType().getKoreanName() + " " + RESET);
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("초나라 점수: " + board.calculateScore(Team.CHO));
        System.out.println("한나라 점수: " + board.calculateScore(Team.HAN));
        System.out.println();
    }

    private static String getColor(Piece piece) {
        if (piece.isSameTeam(domain.Team.CHO)) {
            return GREEN;
        }
        return RED;
    }
}
