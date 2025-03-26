package janggi.view;

import janggi.piece.Piece;
import janggi.team.Team;
import janggi.team.TeamName;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Output {

    private static final String RESET = "\u001B[0m";
    private static final String HAN_RED = "\u001B[31m";
    private static final String CHO_BLUE = "\u001B[34m";

    public void printBoard(Team teamHan, Team teamCho) {
        List<Piece> allPieces = sortBoardPieces(teamHan, teamCho);
        String[][] board = formatBoard();
        setupPieces(allPieces, board);

        System.out.println();
        System.out.println("장기 보드의 현재 상태는 다음과 같습니다.");
        for (int y = 10; y >= 0; y--) {
            System.out.println();
            for (int x = 0; x <= 9; x++) {
                System.out.print(board[x][y]);
            }
        }
        System.out.println();
    }

    private List<Piece> sortBoardPieces(Team teamHan, Team teamCho) {
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(teamHan.getBoard());
        allPieces.addAll(teamCho.getBoard());

        allPieces.sort(Comparator.comparingInt((Piece p) -> p.getPosition().y())
                .thenComparingInt(p -> p.getPosition().x()));

        return allPieces;
    }

    private String[][] formatBoard() {
        String[][] board = new String[10][11];

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 11; y++) {
                if (x == 0 && y == 0) {
                    board[x][y] = " ";
                    continue;
                }
                if (x == 0) {
                    board[x][y] = y - 1 + "";
                    continue;
                }
                if (y == 0) {
                    board[x][y] = x - 1 + "";
                    continue;
                }
                board[x][y] = "_";
            }
        }
        return board;
    }

    private void setupPieces(List<Piece> allPieces, String[][] board) {
        for (Piece piece : allPieces) {
            int x = piece.getPosition().x();
            int y = piece.getPosition().y();
            String color = piece.matchTeam(TeamName.CHO) ? CHO_BLUE : HAN_RED;
            board[x + 1][y + 1] = color + piece.getName() + RESET;
        }
    }

    public void printTeamScore(Team teamHan, Team teamCho) {
        System.out.println();
        System.out.println("현재 각 팀의 점수는 다음과 같습니다");
        System.out.println(CHO_BLUE + "초팀 점수: " + teamCho.getTeamScore() + RESET);
        System.out.println(HAN_RED + "한팀 점수: " + teamHan.getTeamScore() + RESET);
    }
}
