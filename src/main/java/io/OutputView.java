package io;

import domain.game.Board;
import domain.game.JanggiGame;
import domain.game.Piece;
import domain.game.Position;
import domain.game.Turn;
import domain.vo.Col;
import domain.vo.Row;
import domain.vo.Team;

public class OutputView {
    public static final String RED   = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    private static final String REQUEST_SETUP = """
            [%s 진영] 배치를 선택하세요.
            1. 마-상-마-상 (Horse-Elephant-Horse-Elephant)
            2. 마-상-상-마 (Horse-Elephant-Elephant-Horse)
            3. 상-마-마-상 (Elephant-Horse-Horse-Elephant)
            4. 상-마-상-마 (Elephant-Horse-Elephant-Horse)""";
    private static final String REQUEST_MOVE = "[%s 진영] {출발 좌표} {도착 좌표} 형식으로 입력해 수를 두세요. (ex. e6 e5)";

    public void printSetupTable(Team team) {
        String message = String.format(REQUEST_SETUP, team.getTeamName());
        System.out.println(message);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printBoard(Board board, Turn turn) {
        StringBuilder stringBuilder = new StringBuilder();
        String color = turn.getTeam() == Team.HAN ? RED : GREEN;
        stringBuilder.append("--------------------------------------\n");
        stringBuilder.append("현재 턴: [").append(color).append(turn.display()).append(RESET).append(" 진영]\n\n");
        stringBuilder.append("     a   b   c   d   e   f   g   h   i\n");

        for (Row row : Row.values()) {
            stringBuilder.append(String.format("%2s  ", row.getValue()));
            for (Col col : Col.values()) {
                Piece piece = board.getPieceAt(new Position(col, row));
                if (piece != null) {
                    String teamColor = piece.getTeam() == Team.HAN ? RED : GREEN;
                    stringBuilder.append(teamColor).append(piece.display()).append(RESET);
                } else {
                    stringBuilder.append("...");
                }
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder);
    }

    public void printPieceMovement(Turn turn) {
        System.out.printf((REQUEST_MOVE) + "%n", turn.display());
    }
}
