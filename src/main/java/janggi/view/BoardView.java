package janggi.view;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.Type;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.util.Map;

public final class BoardView {

    private static final Map<Type, String> PIECE_NOTATION_KOREAN = Map.of(
            Type.GENERAL, "장",
            Type.CHARIOT, "차",
            Type.CANNON, "포",
            Type.HORSE, "마",
            Type.ELEPHANT, "상",
            Type.GUARD, "사",
            Type.SOLDIER, "병"
    );
    private static final Map<Team, String> TEAM_NOTATION_KOREAN = Map.of(
            Team.CHO, "초 ",
            Team.HAN, "한 "
    );
    private static final String PLAY_TURN_FORMAT = "의 차례입니다.";
    private static final String COLOR_YELLOW = "\u001B[33m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_BLUE = "\u001B[34m";
    private static final String COLOR_END = "\u001B[0m";

    public void displayGame(final Board board) {
        System.out.println();
        System.out.println("  ０ １ ２ ３ ４ ５ ６ ７ ８");
        for (Row row : Row.values()) {
            displayRow(board, row);
        }
    }

    private void displayRow(final Board board, final Row row) {
        System.out.printf("%d ", row.getValue());
        for (Column column : Column.values()) {
            Position position = new Position(row, column);
            displayPosition(board, position);
        }
        System.out.println();
    }

    private static void displayPosition(final Board board, final Position position) {
        if (!board.isPresent(position) && position.isPalace()) {
            System.out.print(COLOR_YELLOW + "＿ " + COLOR_END);
            return;
        }
        if (!board.isPresent(position)) {
            System.out.print("＿ ");
            return;
        }
        displayPiece(board.getPiece(position));
    }

    private static void displayPiece(final Piece piece) {
        final String notation = PIECE_NOTATION_KOREAN.get(piece.getType());
        if (piece.isSameTeam(Team.HAN)) {
            System.out.print(COLOR_RED + notation + " " + COLOR_END);
            return;
        }
        System.out.print(COLOR_BLUE + notation + " " + COLOR_END);
    }

    public void displayTurn(final Board board) {
        final Team team = board.getTurn();
        if (TEAM_NOTATION_KOREAN.get(team).equals("한")) {
            System.out.println(String.format(COLOR_RED + "%s" + COLOR_END + PLAY_TURN_FORMAT,
                    TEAM_NOTATION_KOREAN.get(team)));
            return;
        }
        System.out.println(
                String.format(COLOR_BLUE + "%s" + COLOR_END + PLAY_TURN_FORMAT, TEAM_NOTATION_KOREAN.get(team)));
    }
}
