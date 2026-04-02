package ui.view;

import java.util.Arrays;
import java.util.List;
import ui.dto.BoardStatusDto;
import ui.dto.PieceDto;

public class ResultView {
    public void printBoard(BoardStatusDto dto) {
        String[][] grid = initGrid();

        List<PieceDto> board = dto.getBoard();
        for (PieceDto pieceDto : board) {
            grid[pieceDto.getRow() - 1][pieceDto.getColumn() - 1] = pieceDto.getDescription();
        }

        writeBoard(grid);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
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
