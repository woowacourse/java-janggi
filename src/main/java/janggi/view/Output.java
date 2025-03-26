package janggi.view;

import janggi.piece.*;
import janggi.team.Team;

import java.util.*;

public class Output {

    private static final String RESET = "\u001B[0m";
    private static final String HAN_RED = "\u001B[31m";
    private static final String CHO_BLUE = "\u001B[34m";

    private static final Map<PieceType, String> pieceView = Map.of(
            PieceType.KING, "K",
            PieceType.HORSE, "H",
            PieceType.ELEPHANT, "E",
            PieceType.GUARD, "G",
            PieceType.SOLDIER, "S",
            PieceType.CHARIOT, "C",
            PieceType.CANNON, "P"
    );

    public void printBoard(List<Piece> positioningPieces) {

        String[][] locatedPieces = new String[10][9];

        for (String[] row : locatedPieces) {
            Arrays.fill(row,"_");
        }

        for (Piece piece : positioningPieces) {
            int row = piece.getPosition().row();
            int column = piece.getPosition().column();
            String color = piece.getTeam().equals(Team.CHO) ? CHO_BLUE : HAN_RED;
            Map.Entry<PieceType,String> findEntry = pieceView.entrySet().stream()
                    .filter(entry -> entry.getKey() == piece.getpieceType())
                    .findFirst()
                    .orElseThrow();
            locatedPieces[row - 1][column - 1] = color + findEntry.getValue() + RESET;
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
