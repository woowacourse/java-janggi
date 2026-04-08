package io;

import domain.board.Board;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.game.Turn;
import domain.piece.Team;
import domain.room.GameRoom;
import domain.state.GameResult;
import java.util.List;

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

    public void printSetupTable(Turn turn) {
        String message = String.format(REQUEST_SETUP, turn.display());
        System.out.println(message);
    }

    public void printErrorMessage(String message) {
        System.out.println("\n" + message);
    }

    public void printBoard(Board board, Turn turn) {
        StringBuilder stringBuilder = new StringBuilder();
        appendHeader(stringBuilder, turn);
        appendRows(stringBuilder, board);
        System.out.println(stringBuilder);
    }

    private void appendHeader(StringBuilder stringBuilder, Turn turn) {
        stringBuilder.append("--------------------------------------\n");
        stringBuilder.append("현재 턴: [").append(turn.colorCode(RED, GREEN)).append(turn.display()).append(RESET).append(" 진영]\n\n");
        stringBuilder.append("     a   b   c   d   e   f   g   h   i\n");
    }

    private void appendRows(StringBuilder stringBuilder, Board board) {
        for (Row row : Row.values()) {
            appendRow(stringBuilder, board, row);
        }
    }

    private void appendRow(StringBuilder stringBuilder, Board board, Row row) {
        stringBuilder.append(String.format("%2s  ", row.display()));
        for (Column column : Column.values()) {
            appendCell(stringBuilder, board, new Position(column, row));
        }
        stringBuilder.append("\n");
    }

    private void appendCell(StringBuilder stringBuilder, Board board, Position position) {
        stringBuilder.append(cellDisplay(board, position)).append(" ");
    }

    private String cellDisplay(Board board, Position position) {
        return board.pieceAt(position)
                .map(piece -> piece.colorCode(RED, GREEN) + piece.display() + RESET)
                .orElse("...");
    }

    public void printPieceMovement(Turn turn) {
        System.out.printf((REQUEST_MOVE) + "%n", turn.display());
    }

    public void printCheckMessage(Team checkedTeam) {
        System.out.println("\n[장군] " + checkedTeam.display());
    }

    public void printBikjangQuestion(Team team) {
        System.out.printf("%n[%s 진영] 빅장입니다. 무승부를 선언하시겠습니까? (y/n)%n", team.display());
    }

    public void printRoomMenu(List<GameRoom> rooms) {
        System.out.println("\n--- 장기 게임방 ---");
        if (rooms.isEmpty()) {
            System.out.println("진행 중인 게임방이 없습니다.");
        } else {
            System.out.println("진행 중인 게임방:");
            for (int i = 0; i < rooms.size(); i++) {
                System.out.printf("  [%d] %s%n", i + 1, rooms.get(i).name());
            }
        }
        System.out.println("\n1. 새 게임방 만들기");
        System.out.println("2. 기존 게임방 입장");
        System.out.print("선택: ");
    }

    public void printRoomNamePrompt() {
        System.out.print("게임방 이름을 입력하세요: ");
    }

    public void printRoomNumberPrompt() {
        System.out.print("입장할 게임방 번호를 입력하세요: ");
    }

    public void printRoomEntered(GameRoom room) {
        System.out.printf("'%s' 게임방에 입장했습니다.%n", room.name());
    }

    public void printGameResult(GameResult result, Board board) {
        System.out.println("\n게임 종료!");
        System.out.println(result.message());
        System.out.printf("최종 점수 - 한: %.1f점 / 초: %.1f점%n",
                board.calculateScore(Team.HAN),
                board.calculateScore(Team.CHO));
    }
}
