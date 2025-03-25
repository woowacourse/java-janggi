package view;

import domain.JanggiPosition;
import domain.game.Player;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.state.MovedCannon;
import domain.piece.state.MovedChariot;
import domain.piece.state.MovedElephant;
import domain.piece.state.MovedGeneral;
import domain.piece.state.MovedGuard;
import domain.piece.state.MovedHorse;
import domain.piece.state.MovedSoldierByeong;
import domain.piece.state.MovedSoldierJol;
import java.util.Map;

public class OutputView {

    private static final String NO_PIECE = "_";
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
                if (piece.isEmpty()) {
                    System.out.print(NO_PIECE + SEPERATOR);
                    continue;
                }
                System.out.print(changePiece(piece) + SEPERATOR);
            }
            System.out.println();
        }
        System.out.println(" |1|2|3|4|5|6|7|8|9|\n");
    }

    private static String changePiece(Piece piece) {
        if (piece.getState() instanceof MovedCannon) {
            return "p";
        }
        if (piece.getState() instanceof MovedChariot) {
            return "c";
        }
        if (piece.getState() instanceof MovedElephant) {
            return "e";
        }
        if (piece.getState() instanceof MovedGeneral) {
            return "k";
        }
        if (piece.getState() instanceof MovedGuard) {
            return "s";
        }
        if (piece.getState() instanceof MovedHorse) {
            return "h";
        }
        if (piece.getState() instanceof MovedSoldierByeong) {
            return "b";
        }
        if (piece.getState() instanceof MovedSoldierJol) {
            return "j";
        }
        return " ";
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
