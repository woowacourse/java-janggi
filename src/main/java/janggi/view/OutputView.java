package janggi.view;

import janggi.domain.board.Position;
import janggi.domain.game.Score;
import janggi.domain.game.Turn;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import janggi.domain.piece.Soldier;
import java.util.Map;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printTurn(final Turn turn) {
        System.out.printf("%s 차례입니다.%n", turn.getSide());
    }

    public void printBoard(final Map<Position, Piece> board) {
        String[][] boardOutput = initBoardOutput(board);
        printBoardOutput(boardOutput);
    }

    private static void printBoardOutput(final String[][] boardOutput) {
        for (int i = 9; i >= 0; i--) {
            for (String[] strings : boardOutput) {
                System.out.printf("%s\t", strings[i]);
            }
            System.out.printf("\t%d%n", i + 1);
        }
        System.out.println();
        System.out.println("1\t2\t3\t4\t5\t6\t7\t8\t9");
    }

    private static String[][] initBoardOutput(final Map<Position, Piece> board) {
        String[][] boardOutput = new String[9][10];
        for (int i = 0; i < boardOutput.length; i++) {
            for (int j = 0; j < boardOutput[i].length; j++) {
                Position position = new Position(i + 1, j + 1);
                if (board.containsKey(position)) {
                    Piece piece = board.get(position);
                    String pieceOutput = PieceOutput.getPieceOutputByPieceAndSide(piece);
                    boardOutput[i][j] = pieceOutput;
                    continue;
                }
                boardOutput[i][j] = ".";
            }
        }
        return boardOutput;
    }

    public void printErrorMessage(final Exception e) {
        System.out.println(ERROR_PREFIX + e.getMessage());
    }

    public void printScore(final Score redScore, final Score blueScore) {
        System.out.println("점수 집계");
        System.out.printf("레드팀: %.1f, 블루팀: %.1f%n", redScore.value(), blueScore.value());
    }

    public void printResult(Side side) {
        System.out.printf("%s 팀 승리%n", side);
    }

    enum PieceOutput {
        CANNON("C"),
        ELEPHANT("E"),
        GUARD("G"),
        HORSE("H"),
        KING("K"),
        SOLDIER("S"),
        TANK("T");

        public static final String BLUE_SIDE_COLOR = "\u001B[34m";
        public static final String COLOR_EXIT = "\u001B[0m";
        public static final String RED_SIDE_COLOR = "\u001B[31m";
        private final String output;

        PieceOutput(String output) {
            this.output = output;
        }

        private static String getPieceOutputByPieceAndSide(final Piece piece) {
            String text = getPieceOutputByPiece(piece);
            if (piece.getSide() == Side.RED) {
                return RED_SIDE_COLOR + text + COLOR_EXIT;
            }
            return BLUE_SIDE_COLOR + text + COLOR_EXIT;
        }

        private static String getPieceOutputByPiece(final Piece piece) {
            if (piece instanceof Cannon) {
                return CANNON.output;
            }
            if (piece instanceof Elephant) {
                return ELEPHANT.output;
            }
            if (piece instanceof Guard) {
                return GUARD.output;
            }
            if (piece instanceof Horse) {
                return HORSE.output;
            }
            if (piece instanceof King) {
                return KING.output;
            }
            if (piece instanceof Soldier) {
                return SOLDIER.output;
            }
            return TANK.output;
        }
    }
}
