package view;

import domain.JanggiPosition;
import domain.game.Player;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.Map;

public class OutputView {

    private static final String SEPERATOR = "|";
    private static final int[] FILE = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    private static final int[] RANK = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static void printJanggiBoard(Map<JanggiPosition, Piece> board) {
        System.out.println(" |1|2|3|4|5|6|7|8|9|");
        for (int file : FILE) {
            System.out.print(file + SEPERATOR);
            for (int rank : RANK) {
                JanggiPosition position = new JanggiPosition(file, rank);
                Piece piece = board.get(position);
                System.out.print(changePiece(piece) + SEPERATOR);
            }
            System.out.println();
        }
        System.out.println(" |1|2|3|4|5|6|7|8|9|\n");
    }

    private static String changePiece(Piece piece) {
        return piece.getPieceSymbol().getSymbol();
    }

    public static void printPlayerTurn(Player player) {
        System.out.println("이번 차례는 " + changePlayer(player) + "의 차례입니다. (예시 : 01 81)");
    }

    private static String changePlayer(Player player) {
        if (player.getSide().equals(Side.CHO)) {
            return "초나라";
        }
        return "한나라";
    }

    public static void printScore() {
        System.out.println("점수계산");
    }
}
