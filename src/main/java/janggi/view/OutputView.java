package janggi.view;

import janggi.piece.Cannon;
import janggi.piece.Color;
import janggi.piece.Elephant;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.position.Position;
import java.util.Map;

public class OutputView {

    public void printBoard(Map<Position, Piece> board) {
        String[][] boardData = new String[10][11];

        for (int y = 1; y <= 10; ++y) {
            for (int x = 1; x <= 9; ++x) {
                Position position = new Position(x, y);
                if (board.containsKey(position)) {
                    Piece piece = board.get(position);
                    String pieceOutput = PieceOutput.getPieceOutputByPieceAndSide(piece);
                    boardData[x][y] = pieceOutput;
                    continue;
                }
                boardData[x][y] = ".";
            }
        }

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
        printBoard(boardData);
    }

    private void printBoard(String[][] boardDate) {
        for (int y = 1; y <= 10; ++y) {
            for (int x = 1; x <= 9; ++x) {
                System.out.printf("%s\t", boardDate[x][y]);
            }
            System.out.printf("\t%d%n", y);
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
            if (piece.getSide() == Color.RED) {
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
