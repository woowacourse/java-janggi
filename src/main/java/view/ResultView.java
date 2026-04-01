package view;

import java.util.Arrays;
import java.util.List;

public class ResultView {
    private static final String RETRY_DESCRIPTION_FORMAT = "\n잘못된 입력입니다. 다시 입력하세요. : %s\n";
    private static final String WINNER_PRINT_FORMAT = "\n\n %s나라가 승리했습니다! \n\n";

    public void printBoard(BoardStatusDto dto) {
        String[][] grid = initGrid();

        List<PieceDto> board = dto.board();
        for (PieceDto pieceDto : board) {
            grid[pieceDto.row() - 1][pieceDto.column() - 1] = pieceDto.description();
        }

        writeBoard(grid);
    }

    public void printRetryDescription(IllegalArgumentException e) {
        String exceptionDescription = String.format(RETRY_DESCRIPTION_FORMAT, e.getMessage());
        System.out.println(exceptionDescription);
    }

    public void printWinner(TeamDto winner) {
        String winnerPrintContent = String.format(WINNER_PRINT_FORMAT, winner.teamName());
        System.out.println(winnerPrintContent);
    }

    private String[][] initGrid() {
        String[][] grid = new String[10][9];
        for (int r = 0; r < 10; r++) {
            Arrays.fill(grid[r], "．");
        }
        return grid;
    }

    private void writeBoard(String[][] grid) {
        System.out.println("     a   b  c   d  e  f   g  h  i");
        System.out.println("   +------------------------------+");

        for (int r = 0; r < 10; r++) {
            writeLine(grid, r);
        }

        System.out.println("   +------------------------------+");
    }

    private void writeLine(String[][] grid, int r) {
        System.out.printf("%2d | ", r + 1);

        writePieces(grid[r]);

        System.out.print(" |");
        System.out.println();
    }

    private void writePieces(String[] grid) {
        for (int c = 0; c < 9; c++) {
            System.out.print(grid[c]);
            if (c < 8) {
                System.out.print("  ");
            }
        }
    }
}
