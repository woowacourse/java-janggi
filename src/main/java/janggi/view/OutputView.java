package janggi.view;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;

import java.util.Map;

public class OutputView {
    private static final String COLOR_CODE_EXIT = "\u001B[0m";
    private static final String COLOR_CODE_BLUE = "\u001B[34m";
    private static final String COLOR_CODE_RED = "\u001B[31m";
    private static final String COLOR_CODE_YELLOW = "\u001B[33m";
    private static final String COLOR_CODE_GREEN = "\u001B[32m"; // 초록색

    public void printBoard(final Board board) {
        System.out.print("   ");
        for (int j = 1; j <= 9; j++) {
            System.out.printf("%4s", j);
        }
        System.out.println();

        Map<Position, Piece> pieces = board.getBoard();
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%-3d", i);
            for (int j = 1; j <= 9; j++) {
                Position pos = new Position(i, j);
                Piece piece = pieces.get(pos);
                String color = setPrintColorByPosition(piece, pos);
                String display = (piece != null) ? piece.getName() : "ㅁ";
                System.out.printf("%s%3s%s ", color, display, COLOR_CODE_EXIT);
            }
            System.out.println();
        }
    }

    private String setPrintColorByPosition(final Piece piece, final Position position) {
        if (piece != null) {
            if (piece.getTeam().equals(Team.BLUE)) {
                return COLOR_CODE_BLUE;
            }
            if (piece.getTeam().equals(Team.RED)) {
                return COLOR_CODE_RED;
            }
        }
        if (isPalace(position)) {
            return COLOR_CODE_GREEN; // 궁성은 초록색
        }
        return COLOR_CODE_YELLOW; // 일반 빈 공간은 노란색
    }

    private boolean isPalace(final Position position) {
        int x = position.x();
        int y = position.y();
        return (x >= 1 && x <= 3 && y >= 4 && y <= 6) || (x >= 8 && x <= 10 && y >= 4 && y <= 6);
    }

    public void printWinner(final Team winner) {
        System.out.println(winner.getName() + "팀이 승리했습니다!");
    }

    public void printScore(final Team team, final double score) {
        System.out.println(team.getName() + "팀: " + score + "점");
    }

    public void announceLoadGame() {
        System.out.println("기존 게임이 존재하여 불러옵니다.");
    }

    public void announceNewGame() {
        System.out.println("기존 게임이 존재하지 않습니다. 게임을 새로 생성합니다.");
    }
}