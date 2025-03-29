package view;

import domain.JanggiPosition;
import domain.game.Player;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.Map;

public class OutputView {
    public static final String red = "\u001B[31m";
    public static final String blue = "\u001B[34m";
    public static final String exit = "\u001B[0m";

    private static final String SEPARATOR = "|";
    private static final int[] FILE = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    private static final int[] RANK = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static void printJanggiBoard(Map<JanggiPosition, Piece> board) {
        for (int rank : RANK) {
            char changedRank = (char) ('０' + rank);
            System.out.print("  " + changedRank);
        }
        System.out.println();
        System.out.println("---------------------------------");
        for (int file : FILE) {
            System.out.print(file + SEPARATOR);
            for (int rank : RANK) {
                JanggiPosition position = new JanggiPosition(rank, file);
                Piece piece = board.get(position);
                printPiece(piece);
            }
            System.out.println();
        }
    }

    private static void printPiece(Piece piece) {
        if (piece.getSide().equals(Side.CHO)) {
            System.out.print(blue + changePiece(piece) + "  " + exit);
            return;
        }
        if (piece.getSide().equals(Side.HAN)) {
            System.out.print(red + changePiece(piece) + "  " + exit);
            return;
        }
        System.out.print(changePiece(piece) + "  ");
    }

    private static String changePiece(Piece piece) {
        return piece.getPieceSymbol().getSymbol();
    }

    public static void printCurrentPlayerTurn(Player player) {
        System.out.println();
        System.out.println("이번 차례는 " + changePlayer(player) + "의 차례입니다.");
    }

    private static String changePlayer(Player player) {
        if (player.getSide().equals(Side.CHO)) {
            return "초나라";
        }
        return "한나라";
    }

    public static void printScore(int choScore, int hanScore) {
        System.out.println("\n최종 점수");
        System.out.printf("초나라 : %d점\n", choScore);
        System.out.printf("한나라 : %d점\n", hanScore);
    }
}
