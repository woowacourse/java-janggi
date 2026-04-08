package janggi.view;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import java.util.List;
import java.util.Map;

public class OutputView {

    public void printBoardInitialTypeMessage() {
        System.out.println("상차림 유형 번호를 입력해 주세요 (1.왼상차림 2.오른상차림 3.안상차림 4.바깥상차림)");
    }

    public void printFromPositionMessage() {
        System.out.println("움직일 기물의 위치를 입력하세요:");
    }

    public void printToPositionMessage() {
        System.out.println("이동 시킬 위치를 입력하세요:");
    }

    public void printBoard(Map<Position, AbstractGimul> board, Team team) {
        System.out.println(render(board));
        System.out.println(team.getDisplayName() + "의 차례입니다.");
    }

    private String render(Map<Position, AbstractGimul> board) {
        StringBuilder sb = new StringBuilder();
        sb.append("    1  2  3  4  5  6  7  8  9").append(System.lineSeparator());
        sb.append("  ┌───────────────────────────┐").append(System.lineSeparator());
        appendRows(sb, board);
        sb.append("  └───────────────────────────┘").append(System.lineSeparator());
        return sb.toString();
    }

    private void appendRows(StringBuilder sb, Map<Position, AbstractGimul> board) {
        for (int row = 1; row <= 10; row++) {
            sb.append(renderBoardRow(row, board));
        }
    }

    private StringBuilder renderBoardRow(int row, Map<Position, AbstractGimul> board) {
        StringBuilder sb = new StringBuilder();
        Row currentRow = Row.of(row);
        sb.append(currentRow.getDisplayName()).append(" │");
        for (int col = 1; col <= 9; col++) {
            sb.append(renderBoardColumn(row, col, board));
        }
        sb.append("│").append(System.lineSeparator());
        return sb;
    }

    private StringBuilder renderBoardColumn(int row, int col, Map<Position, AbstractGimul> board) {
        Position position = new Position(Row.of(row), Column.of(col));
        String symbol = board.containsKey(position) ? board.get(position).getSymbol() : "·";
        return new StringBuilder().append(" ").append(String.format("%-2s", symbol));
    }

    public void printScore(double choScore, double hanScore) {
        System.out.println("초: " + choScore + "점 / 한: " + hanScore + "점");
    }

    public void printGameSelectionMessage() {
        System.out.println("1. 새게임 시작 2. 기존 게임 이어하기");
    }

    public void printGameNameMessage() {
        System.out.println("게임방 이름을 입력하세요:");
    }

    public void printGameList(List<String> gameNames) {
        System.out.println("진행 중인 게임 목록:");
        gameNames.forEach(System.out::println);
    }

    public void printNoGameExist() {
        System.out.println("현재 진행 중인 게임이 존재하지 않습니다.");
    }

    public void printGameOver(Team team) {
        System.out.println("게임이 종료되었습니다.");
        System.out.println(team.getDisplayName() + "나라가 승리했습니다!");
    }
}
