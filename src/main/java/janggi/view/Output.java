package janggi.view;

import janggi.board.BoardCho;
import janggi.board.BoardHan;
import janggi.piece.Piece;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Output {

    private static final String RESET = "\u001B[0m";
    private static final String HAN_RED = "\u001B[31m";
    private static final String CHO_BLUE = "\u001B[34m";

    public void printBoard(BoardHan boardHan, BoardCho boardCho) {
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(boardHan.getBoard());
        allPieces.addAll(boardCho.getBoard());

        allPieces.sort(Comparator.comparingInt((Piece p) -> p.getPosition().getY())
                .thenComparingInt(p -> p.getPosition().getX()));

        String[][] board = new String[9][10];

        for (int x = 0; x <= 8; x++) {
            for (int y = 0; y <= 9; y++) {
                board[x][y] = "_";
            }
        }

        for (Piece piece : allPieces) {
            int x = piece.getPosition().getX();
            int y = piece.getPosition().getY();
            String color = piece.getTeam().equals(Team.CHO) ? CHO_BLUE : HAN_RED;
            board[x][y] = color + piece.getName() + RESET;
        }

        for (int y = 9; y >= 0; y--) {
            System.out.println();
            for (int x = 0; x <= 8; x++) {
                System.out.print(board[x][y]);
            }
        }

        System.out.println();
    }
}
