package janggi.view;

import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Map;

public class OutputView {

    private static final String RED_COLOR_CODE = "\u001B[31m";
    private static final String GREEN_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";

    private static final String EMPTY_SPACE = "ㅤ";

    private static final int BOARD_WIDTH = 9;
    private static final int BOARD_HEIGHT = 10;
    private static final String[][] JANGGI_BOARD_ARR = new String[BOARD_HEIGHT + 1][BOARD_WIDTH + 1];

    public void printCallInGame() {
        System.out.println("이전의 게임을 불러 왔습니다.");
    }

    public void printNewGame() {
        System.out.println("불러올 게임이 존재하지 않습니다. 새로운 게임을 생성합니다.");
    }

    public void printJanggiBoard(final Map<Position, Piece> janggiBoard) {
        initializeJanggiBoard(janggiBoard);
        printFormattedJanggiBoard();
    }

    private void initializeJanggiBoard(final Map<Position, Piece> janggiBoard) {
        setBoardWithEmptySpaces();
        placeChessPieces(janggiBoard);
        setBoardLabels();
    }

    private void setBoardWithEmptySpaces() {
        for (int i = 1; i <= BOARD_HEIGHT; i++) {
            for (int j = 1; j <= BOARD_WIDTH; j++) {
                JANGGI_BOARD_ARR[i][j] = " | " + EMPTY_SPACE;
            }
        }
    }

    private void placeChessPieces(final Map<Position, Piece> janggiBoard) {
        for (final Position position : janggiBoard.keySet()) {
            final int row = position.row() + 1;
            final int col = position.col() + 1;

            final String pieceName = getColoredPieceName(janggiBoard.get(position));
            JANGGI_BOARD_ARR[row][col] = " | " + pieceName;
        }
    }

    private String getColoredPieceName(final Piece piece) {
        final String typeName = piece.getPieceType().getValue();
        if (piece.isChoNation()) {
            return GREEN_COLOR_CODE + typeName + EXIT_CODE;
        }
        if (piece.isHanNation()) {
            return RED_COLOR_CODE + typeName + EXIT_CODE;
        }
        return typeName;
    }

    private void setBoardLabels() {
        JANGGI_BOARD_ARR[0][0] = EMPTY_SPACE + EMPTY_SPACE;

        for (int row = 1; row <= BOARD_HEIGHT; row++) {
            JANGGI_BOARD_ARR[row][0] = (row - 1) + EMPTY_SPACE;
        }

        for (int col = 1; col <= BOARD_WIDTH; col++) {
            JANGGI_BOARD_ARR[0][col] = EMPTY_SPACE + " " + (col - 1) + EMPTY_SPACE;
        }
    }

    private void printFormattedJanggiBoard() {
        System.out.println();
        for (final String[] row : OutputView.JANGGI_BOARD_ARR) {
            for (final String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    public void printSuccessMove() {
        System.out.println("성공적으로 기물을 이동했습니다.");
    }

    public void printEndGame() {
        System.out.println("왕이 죽었음으로 게임이 종료됩니다.");
    }

    public void printScore(final double chuScore, final double hanScore) {
        System.out.println("초나라 점수: " + chuScore);
        System.out.println("한나라 점수: " + hanScore);
    }

    public void printWinner(final String currentTurnTeam) {
        System.out.println(currentTurnTeam + "가 승리하였습니다.");
    }

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }
}
