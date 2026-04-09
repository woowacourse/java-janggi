package janggi.view;

import janggi.domain.BoardView;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;

public class OutputView {
    private static final int ROW_SIZE = 10;
    private static final int COL_SIZE = 9;

    public void printGameStart(Long gameId) {
        System.out.println("게임 " + gameId + "을(를) 시작합니다.");
    }

    public void printResume(Long gameId, Team currentTurn) {
        System.out.println("게임 " + gameId + "을(를) 이어서 진행합니다. 현재 턴: " + currentTurn);
    }

    public void printCurrentTurn(Team team) {
        System.out.println(team + "의 차례입니다.");
    }

    public void printResult(Team winner, int choScore, int hanScore) {
        printGameEnd(winner);
        printScore(Team.CHO, choScore);
        printScore(Team.HAN, hanScore);
    }

    private void printGameEnd(Team winner) {
        System.out.println("게임이 종료되었습니다. 승자: " + winner);
    }

    private void printScore(Team team, int score) {
        System.out.println(team + " 점수: " + score);
    }

    public void printBoard(BoardView board) {
        System.out.println();
        printColumnHeader();
        for (int row = 0; row < ROW_SIZE; row++) {
            printRow(board, row);
        }
        System.out.println();
    }

    private void printColumnHeader() {
        StringBuilder sb = new StringBuilder("  ");
        for (int col = 0; col < COL_SIZE; col++) {
            sb.append("  ").append(col).append(" ");
        }
        System.out.println(sb);
    }

    private void printRow(BoardView board, int row) {
        StringBuilder sb = new StringBuilder();
        sb.append(row).append(" ");
        for (int col = 0; col < COL_SIZE; col++) {
            String display = board.findByPosition(new Position(row, col)).display();
            sb.append("[").append(display).append("]");
        }
        System.out.println(sb);
    }

}
