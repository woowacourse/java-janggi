package view;

import game.Board;
import game.Team;
import java.util.Map;
import piece.Piece;
import position.Position;
import position.PositionFile;
import position.PositionRank;

public final class OutputView {
    private static final String BLUE = "\u001B[34m";  // 초나라 (파란색)
    private static final String RED = "\u001B[31m";   // 한나라 (빨간색)
    private static final String RESET = "\u001B[0m";

    public void displayBoard(Board board) {
        System.out.println();

        Team team1 = board.getTeam1();  // CHO
        Team team2 = board.getTeam2();  // HAN
        Map<Position, Piece> team1Pieces = team1.getPieces();
        Map<Position, Piece> team2Pieces = team2.getPieces();

        for (int rank = PositionRank.MAX_VALUE; rank >= 1; rank--) {
            displayRow(team1Pieces, team2Pieces, new PositionRank(rank));
        }

        System.out.print("  ");
        for (PositionFile file : PositionFile.values()) {
            System.out.print(file);
        }
        System.out.println();
    }

    private void displayRow(Map<Position, Piece> team1Board, Map<Position, Piece> team2Board, PositionRank rank) {
        System.out.print(rank.value() + " ");

        for (PositionFile file : PositionFile.values()) {
            Position position = new Position(file, rank);
            displayPosition(team1Board, team2Board, position);
        }
        System.out.println();
    }

    private void displayPosition(Map<Position, Piece> team1Board, Map<Position, Piece> team2Board, Position position) {
        if (team1Board.containsKey(position)) {
            displayPiece(team1Board.get(position), true);  // true: CHO
        } else if (team2Board.containsKey(position)) {
            displayPiece(team2Board.get(position), false); // false: HAN
        } else {
            System.out.print("ㅡ");
        }
    }

    private void displayPiece(Piece piece, boolean isCho) {
        String color = isCho ? BLUE : RED;
        System.out.print(color + piece.getType().name().charAt(0) + RESET);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
