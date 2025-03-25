package view;

import static view.JanggiPieceTypeDisplay.*;

import domain.position.JanggiPosition;
import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final List<Character> JANGGI_BOARD_RANKS_INDEX = List.of('1', '2', '3', '4', '5', '6', '7', '8', '9');
    private static final List<Integer> JANGGI_BOARD_RANKS = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
    private static final List<Integer> JANGGI_BOARD_FILES = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private static final List<JanggiPosition> PALACE_OF_CHO = List.of(
            new JanggiPosition(0, 4),
            new JanggiPosition(0, 5),
            new JanggiPosition(0, 6),
            new JanggiPosition(9, 4),
            new JanggiPosition(9, 5),
            new JanggiPosition(9, 6),
            new JanggiPosition(8, 4),
            new JanggiPosition(8, 5),
            new JanggiPosition(8, 6)
    );
    private static final List<JanggiPosition> PALACE_OF_HAN = List.of(
            new JanggiPosition(1, 4),
            new JanggiPosition(1, 5),
            new JanggiPosition(1, 6),
            new JanggiPosition(2, 4),
            new JanggiPosition(2, 5),
            new JanggiPosition(2, 6),
            new JanggiPosition(3, 4),
            new JanggiPosition(3, 5),
            new JanggiPosition(3, 6)
    );
    private static final String CHO_COLOR_PREFIX = "\u001B[32m";
    private static final String CHO_PALACE_COLOR_PREFIX = "\u001B[38;5;120m";
    private static final String HAN_COLOR_PREFIX = "\u001B[31m";
    private static final String HAN_PALACE_COLOR_PREFIX = "\u001B[38;5;210m";
    private static final String COLOR_SUFFIX = "\u001B[0m";

    public void printInitBoardMessage() {
        System.out.println("장기판을 초기화합니다.");
    }

    public void printBoard(Map<JanggiPosition, JanggiPiece> board) {
        System.out.print(" ");
        for (Character rank : JANGGI_BOARD_RANKS_INDEX) {
            System.out.print(" " + (char) (rank - '0' + '０'));
        }
        System.out.println();
        for (int rank : JANGGI_BOARD_RANKS) {
            System.out.print(rank + " ");
            for (int file : JANGGI_BOARD_FILES) {
                JanggiPosition position = new JanggiPosition(rank, file);
                JanggiPiece piece = board.get(position);
                if (piece.isEmpty() && PALACE_OF_CHO.contains(position)) {
                    System.out.print(getMessageWithColorOfPalace(JanggiSide.CHO, "ㅁ "));
                    continue;
                }
                if (piece.isEmpty() && PALACE_OF_HAN.contains(position)) {
                    System.out.print(getMessageWithColorOfPalace(JanggiSide.HAN, "ㅁ "));
                    continue;
                }
                System.out.print(getMessageWithColorOfSide(piece.getSide(), getJanggiTypeDisplay(piece.getType(), piece.getSide())) + " ");
            }
            System.out.print(LINE_SEPARATOR);
        }
    }

    private String getMessageWithColorOfPalace(JanggiSide side, String message) {
        if (side == JanggiSide.CHO) {
            return CHO_PALACE_COLOR_PREFIX + message + COLOR_SUFFIX;
        }
        if (side == JanggiSide.HAN) {
            return HAN_PALACE_COLOR_PREFIX + message + COLOR_SUFFIX;
        }
        return message;
    }

    public void printTurnMessage(JanggiSide janggiSide) {
        System.out.println(LINE_SEPARATOR
                + getMessageWithColorOfSide(janggiSide, JanggiSideDisplay.getJanggiSideDisplay(janggiSide))
                + "의 차례입니다.");
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printWinningMessage(JanggiSide nowTurn) {
        System.out.println(LINE_SEPARATOR + getMessageWithColorOfSide(nowTurn, JanggiSideDisplay.getJanggiSideDisplay(nowTurn)) + "의 승리입니다!" + LINE_SEPARATOR);
    }

    public void printScore(JanggiSide side, int remainingPiecesTotalScore) {
        System.out.println(getMessageWithColorOfSide(side, side.name()) + ": %d점".formatted(remainingPiecesTotalScore));
    }

    private String getMessageWithColorOfSide(JanggiSide side, String message) {
        if (side == JanggiSide.CHO) {
            return CHO_COLOR_PREFIX + message + COLOR_SUFFIX;
        }
        if (side == JanggiSide.HAN) {
            return HAN_COLOR_PREFIX + message + COLOR_SUFFIX;
        }
        return message;
    }
}
