package view;

import constant.BoardSpec;
import domain.Position;
import domain.Side;
import domain.piece.Piece;
import java.util.Map;

public class OutputView {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String BOARD_HEADER = "    1  2  3  4  5  6  7  8  9";
    private static final String CHO_TURN_MESSAGE = "       초의 차례입니다.";
    private static final String HAN_TURN_MESSAGE = "       한의 차례입니다.";
    private static final String SINGLE_DIGIT_ROW_FORMAT = " %d  ";
    private static final String DOUBLE_DIGIT_ROW_FORMAT = "%d  ";
    private static final String PIECE_FORMAT = "%s%s ";
    private static final String CURRENT_TOTAL_SCORE_MESSAGE = "초: %.1f점          한: %.1f점\n";
    private static final String WINNER_IS_CHO = "       초의 승리입니다!";
    private static final String WINNER_IS_HAN = "       한의 승리입니다!";

    public void printBoardStatus(Map<Position, Piece> board) {
        System.out.println();
        System.out.println(BOARD_HEADER);

        for (int y = BoardSpec.MIN_Y; y <= BoardSpec.MAX_Y; y++) {
            printRowNumber(y);
            for (int x = 1; x <= 9; x++) {
                Piece piece = board.get(Position.of(x, y));
                printPiece(piece);
            }
            System.out.println(RESET);
        }
    }

    public void printCurrentTurn(Side currentSide) {
        System.out.println();
        if (currentSide == Side.CHO) {
            System.out.println(CHO_TURN_MESSAGE);
            return;
        }
        System.out.println(HAN_TURN_MESSAGE);
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
    }

    public void printCurrentTotalScore(double choTotalScore, double hanTotalScore) {
        System.out.printf(CURRENT_TOTAL_SCORE_MESSAGE, choTotalScore, hanTotalScore);
    }

    public void printResult(Side winner) {
        if (winner == Side.CHO) {
            System.out.println(WINNER_IS_CHO);
            return;
        }
        System.out.println(WINNER_IS_HAN);
    }

    private void printRowNumber(int y) {
        if (y < BoardSpec.MAX_Y) {
            System.out.printf(SINGLE_DIGIT_ROW_FORMAT, y);
        } else {
            System.out.printf(DOUBLE_DIGIT_ROW_FORMAT, y);
        }
    }

    private void printPiece(Piece piece) {
        String color = getColor(piece);
        System.out.printf(PIECE_FORMAT, color, piece.getName());
    }

    private String getColor(Piece piece) {
        if (piece.isSameSide(Side.HAN)) {
            return RED;
        }
        if (piece.isSameSide(Side.CHO)) {
            return BLUE;
        }
        return RESET;
    }
}
