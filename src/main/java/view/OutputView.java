package view;

import static view.JanggiPieceTypeDisplay.*;

import domain.JanggiPosition;
import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final List<Character> JANGGI_BOARD_RANKS_INDEX = List.of('1', '2', '3', '4', '5', '6', '7', '8', '9');
    private static final List<Integer> JANGGI_BOARD_RANKS = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
    private static final List<Integer> JANGGI_BOARD_FILES = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private static final String CHO_COLOR_PREFIX = "\u001B[32m";
    private static final String HAN_COLOR_PREFIX = "\u001B[31m";
    private static final String COLOR_SUFFIX = "\u001B[0m";

    public void printInitBoardMessage() {
        System.out.println("장기판을 초기화합니다." + LINE_SEPARATOR);
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
                if (piece.getSide() == JanggiSide.CHO) {
                    System.out.print(CHO_COLOR_PREFIX + getJanggiTypeDisplay(piece.getType()) + COLOR_SUFFIX + " ");
                }
                if (piece.getSide() == JanggiSide.HAN) {
                    System.out.print(HAN_COLOR_PREFIX + getJanggiTypeDisplay(piece.getType()) + COLOR_SUFFIX + " ");
                }
                if (piece.getSide() == JanggiSide.NONE) {
                    System.out.print(getJanggiTypeDisplay(piece.getType()) + " ");
                }
            }
            System.out.print(LINE_SEPARATOR);
        }
    }
}
