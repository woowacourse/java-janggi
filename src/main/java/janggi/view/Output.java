package janggi.view;

import janggi.piece.Piece;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Output {

    private static final String RESET = "\u001B[0m";
    private static final String HAN_RED = "\u001B[31m";
    private static final String CHO_BLUE = "\u001B[34m";
/*
    public void printBoard(Board board) {
        List<Piece> allPieces = board.getBoard();

        allPieces.sort(Comparator.comparingInt((Piece p) -> p.getPosition().getColumn())
                .thenComparingInt(p -> p.getPosition().getRow()));

        String[][] locatedPieces = new String[9][10];

        for (int x = 0; x <= 8; x++) {
            for (int y = 0; y <= 9; y++) {
                locatedPieces[x][y] = "_";
            }
        }

        for (Piece piece : allPieces) {
            int x = piece.getPosition().getRow();
            int y = piece.getPosition().getColumn();
            String color = piece.getTeam().equals(Team.CHO) ? CHO_BLUE : HAN_RED;
            locatedPieces[x][y] = color + piece.getName() + RESET;
        }

        for (int y = 9; y >= 0; y--) {
            System.out.println();
            for (int x = 0; x <= 8; x++) {
                System.out.print(locatedPieces[x][y]);
            }
        }

        System.out.println();
    }*/
}
