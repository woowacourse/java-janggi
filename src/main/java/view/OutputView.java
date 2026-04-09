package view;

import controller.dto.CurrentBoardStatus;
import controller.dto.CurrentScore;
import controller.dto.MoveStatus;
import domain.Team;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final String CURRENT_TEAM_GUIDE = "이번 턴은 %s나라 차례입니다.";
    private static final String MOVE_STATUS = "%s나라 기물 %s을(를) %d,%d로 이동하였습니다.";
    private static final String GAME_WINNER_GUIDE = "장기 게임이 종료되었습니다. 게임의 우승자는 %s나라 입니다.";
    private static final String CURRENT_SCORE_GUIDE = "[현재 점수]";
    private static final String TEAM_SCORE_FORMAT = "%s나라 : %d점";

    private static final int BOARD_ROW_SIZE = 10;
    private static final int BOARD_COLUMN_SIZE = 9;
    private static final String EMPTY_CELL = "   ";

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";

    public void printCurrentBoard(List<CurrentBoardStatus> statuses) {
        String[][] board = createEmptyBoard();
        placePieces(board, statuses);

        printHeader();
        printSeparator();

        for (int row = 1; row <= BOARD_ROW_SIZE; row++) {
            printRow(board, row);
            printSeparator();
        }
    }

    public void printMoveStatus(MoveStatus moveStatus) {
        System.out.println(String.format(MOVE_STATUS, moveStatus.teamName(),
                moveStatus.pieceName(),
                moveStatus.destinationRow(),
                moveStatus.destinationColumn())
        );
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printGameWinner(String winner) {
        System.out.println(String.format(GAME_WINNER_GUIDE, winner));
    }

    public void printCurrentScore(List<CurrentScore> currentScores) {
        System.out.println(CURRENT_SCORE_GUIDE);
        for (CurrentScore currentScore : currentScores) {
            System.out.println(String.format(TEAM_SCORE_FORMAT, currentScore.teamName(), currentScore.score()));
        }
    }

    public void printCurrentTurnTeam(String teamName) {
        System.out.println(String.format(CURRENT_TEAM_GUIDE, teamName));
    }

    /**
     * 헬퍼 메서드
     */
    private String[][] createEmptyBoard() {
        String[][] board = new String[BOARD_ROW_SIZE][BOARD_COLUMN_SIZE];

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int column = 0; column < BOARD_COLUMN_SIZE; column++) {
                board[row][column] = EMPTY_CELL;
            }
        }

        return board;
    }

    private void placePieces(String[][] board, List<CurrentBoardStatus> statuses) {
        for (CurrentBoardStatus status : statuses) {
            int rowIndex = status.row() - 1;
            int colIndex = status.column() - 1;

            board[rowIndex][colIndex] = formatPiece(status.pieceType(), status.team());
        }
    }

    private void printHeader() {
        System.out.println("        1      2      3      4      5      6      7      8      9");
    }

    private void printSeparator() {
        System.out.println("    +------+------+------+------+------+------+------+------+------+");
    }

    private void printRow(String[][] board, int row) {
        System.out.printf("%2d  |", row);

        for (int col = 1; col <= BOARD_COLUMN_SIZE; col++) {
            System.out.printf(" %-4s |", board[row - 1][col - 1]);
        }

        System.out.println();
    }

    private String formatPiece(String pieceType, String team) {
        if ("초".equals(team)) {
            return BLUE + " " + pieceType + " " + RESET;
        }
        if ("한".equals(team)) {
            return RED + " " + pieceType + " " + RESET;
        }
        return pieceType;
    }
}
