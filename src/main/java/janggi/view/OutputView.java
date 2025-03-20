package janggi.view;

import janggi.Position;
import janggi.Side;
import janggi.piece.Cannon;
import janggi.piece.Elephant;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.Map;

public class OutputView {

    public void printBoard(Map<Position, Piece> board) {
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
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < boardOutput.length; j++) {
                System.out.printf("%s\t", boardOutput[j][i]);
            }
            System.out.printf("\t%d%n", i + 1);
        }
        System.out.println();
        System.out.println("1\t2\t3\t4\t5\t6\t7\t8\t9");
    }

    enum PieceOutput {
        CANNON("C"),
        ELEPHANT("E"),
        GUARD("G"),
        HORSE("H"),
        KING("K"),
        SOLDIER("S"),
        TANK("T");

        private final String output;

        PieceOutput(String output) {
            this.output = output;
        }

        private static String getPieceOutputByPieceAndSide(Piece piece) {
            if (piece.getSide() == Side.RED) {
                return getPieceOutputByPiece(piece);
            }
            return getPieceOutputByPiece(piece).toLowerCase();
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
