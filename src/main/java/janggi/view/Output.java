package janggi.view;

import janggi.piece.*;
import janggi.team.Team;

import java.util.*;

public class Output {

    private static final String RESET = "\u001B[0m";
    private static final String HAN_RED = "\u001B[31m";
    private static final String CHO_BLUE = "\u001B[34m";

    public void printBoard(List<Piece> positioningPieces) {

        Map<String, Class<? extends Piece>> pieceView = Map.of(
                "K", King.class,
                "H", Horse.class,
                "E", Elephant.class,
                "G", Guard.class,
                "S", Soldier.class,
                "C", Chariot.class,
                "P", Cannon.class
        );

        String[][] locatedPieces = new String[10][9];

        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 8; y++) {
                locatedPieces[x][y] = "_";
            }
        }

        for (Piece piece : positioningPieces) {
            int row = piece.getPosition().getRow();
            int column = piece.getPosition().getColumn();
            String color = piece.getTeam().equals(Team.CHO) ? CHO_BLUE : HAN_RED;
            Map.Entry<String, Class<? extends Piece>> findEntry = pieceView.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(piece.getClass()))
                    .findFirst()
                    .orElseThrow();
            locatedPieces[row - 1][column - 1] = color + findEntry.getKey() + RESET;
        }

        for (int i = 0; i <= 9; i++) {
            System.out.println();
            for (int j = 0; j <= 8; j++) {
                System.out.print(locatedPieces[i][j]);
            }
        }

        System.out.println();
    }
}
