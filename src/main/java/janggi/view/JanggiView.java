package janggi.view;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.Type;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.score.ScoreBoard;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class JanggiView {

    private final Scanner scanner = new Scanner(System.in);
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
            Team.CHO, "초",
            Team.HAN, "한"
    );
    private static final String PLAY_TURN_FORMAT = "의 차례입니다.";
    private static final String COLOR_YELLOW = "\u001B[33m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_BLUE = "\u001B[34m";
    private static final String COLOR_END_FORMAT = "\u001B[0m";
    private static final String HEADER_SCOREBOARD = "===== 점수판 =====";
    private static final String SCORE_FORMAT = " : %.1f점";
    private static final String HEADER_END = "게임을 종료합니다.";
    private static final String WINNER_FORMAT_FRONT = "승자는%s %s%s입니다!";

    public String read() {
        return scanner.nextLine();
    }

    public void displaySetupOption() {
        System.out.println("게임을 시작합니다.");
        System.out.println("상차림을 입력하세요.\n"
                + "0. 저장된 게임\n"
                + "1. 안상\n"
                + "2. 바깥상\n"
                + "3. 오른상\n"
                + "4. 왼상");
    }

    public void displayGame(final Board board) {
        System.out.println();
        System.out.println("  ０ １ ２ ３ ４ ５ ６ ７ ８");
        for (Row row : Row.values()) {
            displayRow(board, row);
        }
        System.out.println();
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
            System.out.print(COLOR_YELLOW + "＿ " + COLOR_END_FORMAT);
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
            System.out.print(COLOR_RED + notation + " " + COLOR_END_FORMAT);
            return;
        }
        System.out.print(COLOR_BLUE + notation + " " + COLOR_END_FORMAT);
    }

    public void displayTurn(final Board board) {
        final Team team = board.getTurn();
        final String teamName = TEAM_NOTATION_KOREAN.get(team);
        if (teamName.equals("한")) {
            System.out.println(COLOR_RED + teamName + COLOR_END_FORMAT + PLAY_TURN_FORMAT);
            return;
        }
        System.out.println(COLOR_BLUE + teamName + COLOR_END_FORMAT + PLAY_TURN_FORMAT);
    }

    public void displayScore(final ScoreBoard scoreBoard) {
        System.out.println(HEADER_SCOREBOARD);
        System.out.println(
                String.format(COLOR_RED + "한" + COLOR_END_FORMAT + SCORE_FORMAT, scoreBoard.getScore(Team.HAN)));
        System.out.println(
                String.format(COLOR_BLUE + "초" + COLOR_END_FORMAT + SCORE_FORMAT, scoreBoard.getScore(Team.CHO)));
        System.out.println();
    }

    public void displayEnd(final Board board) {
        final Team winner = board.findWinner();
        System.out.println();
        System.out.println(HEADER_END);
        if (winner == Team.HAN) {
            System.out.println(
                    String.format(WINNER_FORMAT_FRONT, COLOR_RED, TEAM_NOTATION_KOREAN.get(winner), COLOR_END_FORMAT));
            return;
        }
        System.out.println(
                String.format(WINNER_FORMAT_FRONT, COLOR_BLUE, TEAM_NOTATION_KOREAN.get(winner), COLOR_END_FORMAT));

    }

    public void displayUnfinishedGame(final List<Integer> notFinishedGameIds) {
        System.out.println();
        System.out.println("===== 게임 기록 =====");
        for (Integer id : notFinishedGameIds) {
            System.out.println(String.format(" - %d번 게임", id));
        }
        System.out.println("원하는 게임을 골라주세요.");
    }

    public void displayError(final String message) {
        System.out.println("[ERROR] " + message);
    }
}
