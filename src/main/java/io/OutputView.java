package io;

import domain.game.Board;
import domain.game.Piece;
import domain.game.Position;
import domain.game.Turn;
import domain.vo.Col;
import domain.vo.Row;
import domain.vo.Team;

public class OutputView {
    private static final String REQUEST_SETUP = """
            [%s 진영] 배치를 선택하세요.
            1. 마-상-마-상 (Horse-Elephant-Horse-Elephant)
            2. 마-상-상-마 (Horse-Elephant-Elephant-Horse)
            3. 상-마-마-상 (Elephant-Horse-Horse-Elephant)
            4. 상-마-상-마 (Elephant-Horse-Elephant-Horse)""";

    public void printSetupTable(Team team) {
        String message = String.format(REQUEST_SETUP, team.getTeamName());
        System.out.println(message);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printBoard(Board board, Turn turn) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--------------------------------------\n");
        stringBuilder.append("현재 턴: [").append(turn.display()).append(" 진영]\n\n");
        stringBuilder.append("     a   b   c   d   e   f   g   h   i\n");

        for (Row row : Row.values()) {
            stringBuilder.append(String.format("%2s  ", row.getValue()));
            for (Col col : Col.values()) {
                Piece piece = board.getPieceAt(new Position(col, row));
                stringBuilder.append(piece != null ? piece.display() : "...");
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder);
    }
}
