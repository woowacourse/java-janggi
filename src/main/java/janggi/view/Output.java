package janggi.view;

import janggi.piece.Piece;
import janggi.team.Team;
import janggi.team.TeamName;
import janggi.team.Teams;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Output {

    private static final String RESET = "\u001B[0m";
    private static final String HAN_RED = "\u001B[31m";
    private static final String CHO_BLUE = "\u001B[34m";
    private static final String SPACE = " ";
    private static final String UNDERLINE = "_";

    private static final int BOARD_X_SIZE = 10;
    private static final int BOARD_Y_SIZE = 11;
    private static final int INDEX_ONE = 1;
    private static final int INDEX_ZERO = 0;

    public void printBoard(Teams teams) {
        Team teamCho = teams.getTeamCho();
        Team teamHan = teams.getTeamHan();

        List<Piece> allPieces = sortBoardPieces(teamHan, teamCho);
        String[][] board = formatBoard();
        setupPieces(allPieces, board);

        System.out.println();
        System.out.println("장기 보드의 현재 상태는 다음과 같습니다.");
        for (int y = BOARD_Y_SIZE - INDEX_ONE; y >= INDEX_ZERO; y--) {
            System.out.println();
            for (int x = INDEX_ZERO; x <= BOARD_X_SIZE - INDEX_ONE; x++) {
                System.out.print(board[x][y]);
            }
        }
        System.out.println();
    }

    private List<Piece> sortBoardPieces(Team teamHan, Team teamCho) {
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(teamHan.getBoard());
        allPieces.addAll(teamCho.getBoard());

        allPieces.sort(Comparator.comparingInt((Piece piece) -> piece.getPosition().y())
                .thenComparingInt(piece -> piece.getPosition().x()));

        return allPieces;
    }

    private String[][] formatBoard() {
        String[][] board = new String[BOARD_X_SIZE][BOARD_Y_SIZE];

        for (int x = INDEX_ZERO; x < BOARD_X_SIZE; x++) {
            for (int y = INDEX_ZERO; y < BOARD_Y_SIZE; y++) {
                if (x == INDEX_ZERO && y == INDEX_ZERO) {
                    board[x][y] = SPACE;
                    continue;
                }
                if (x == 0) {
                    board[x][y] = String.valueOf(y - INDEX_ONE);
                    continue;
                }
                if (y == 0) {
                    board[x][y] = String.valueOf(x - INDEX_ONE);
                    continue;
                }
                board[x][y] = UNDERLINE;
            }
        }
        return board;
    }

    private void setupPieces(List<Piece> allPieces, String[][] board) {
        for (Piece piece : allPieces) {
            int x = piece.getPosition().x();
            int y = piece.getPosition().y();
            String color = piece.matchTeam(TeamName.CHO) ? CHO_BLUE : HAN_RED;
            board[x + INDEX_ONE][y + INDEX_ONE] = color + piece.getName() + RESET;
        }
    }

    public void printTeamScore(Teams teams) {
        System.out.println();
        System.out.println("현재 각 팀의 점수는 다음과 같습니다");
        System.out.println(CHO_BLUE + "초팀 점수: " + teams.checkTeamChoScore() + RESET);
        System.out.println(HAN_RED + "한팀 점수: " + teams.checkTeamHanScore() + RESET);
    }
}
