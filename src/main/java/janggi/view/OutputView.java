package janggi.view;

import janggi.board.Position;
import janggi.piece.Cannon;
import janggi.piece.Elephant;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Side;
import janggi.piece.Soldier;
import java.util.Map;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printBoard(Map<Position, Piece> board) {
        String[][] boardOutput = initBoardOutput(board);
        printBoardOutput(boardOutput);
    }

    private static void printBoardOutput(String[][] boardOutput) {
        for (int i = 9; i >= 0; i--) {
            for (String[] strings : boardOutput) {
                System.out.printf("%s\t", strings[i]);
            }
            System.out.printf("\t%d%n", i + 1);
        }
        System.out.println();
        System.out.println("1\t2\t3\t4\t5\t6\t7\t8\t9");
    }

    private static String[][] initBoardOutput(Map<Position, Piece> board) {
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

    public void printErrorMessage(Exception e) {
        System.out.println(ERROR_PREFIX + e.getMessage());
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

        private static String getPieceOutputByPieceAndSide(Piece piece) {
            String text = getPieceOutputByPiece(piece);
            if (piece.getSide() == Side.RED) {
                return RED_SIDE_COLOR + text + COLOR_EXIT;
            }
            return BLUE_SIDE_COLOR + text + COLOR_EXIT;
        }

        private static String getPieceOutputByPiece(Piece piece) {
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
