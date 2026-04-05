package ui.view;

import domain.piece.Team;
import java.util.Arrays;
import java.util.List;
import ui.dto.BoardStatusDto;
import ui.dto.PieceDto;

public class ResultView {
    private static final String CHO_KOREAN_DESCRIPTION = "초나라";
    private static final String HAN_KOREAN_DESCRIPTION = "한나라";

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
            Arrays.fill(grid[r], "＋");
        }
        return grid;
    }

    private void writeBoard(String[][] grid) {
        // 알파벳 헤더의 사이 간격을 한 칸씩 줄여서 타이트하게 맞췄습니다.
        System.out.println("    a   b    c   d    e   f   g    h   i");

        for (int r = 0; r < 10; r++) {
            writeLine(grid, r);
            if (r < 9) {
                writeConnector(r);
            }
        }
    }

    private void writeLine(String[][] grid, int r) {
        System.out.printf("%2d  ", r + 1);
        writePieces(grid[r]);
        System.out.println();
    }

    private void writePieces(String[] grid) {
        for (int c = 0; c < 9; c++) {
            System.out.print(grid[c]);
            if (c < 8) {
                System.out.print(" - ");
            }
        }
    }

    private void writeConnector(int r) {
        System.out.print("    ");

        for (int c = 0; c < 9; c++) {
            if (c == 8) {
                System.out.println("|");
                break;
            }

            // 세로선(|) 라인의 간격도 한 칸씩 줄여서 폰트 너비 오차를 잡았습니다.
            if ((r == 0 || r == 7) && c == 3) {
                System.out.print("|  \\ ");
            } else if ((r == 0 || r == 7) && c == 4) {
                System.out.print("| /  ");
            } else if ((r == 1 || r == 8) && c == 3) {
                System.out.print("|  / ");
            } else if ((r == 1 || r == 8) && c == 4) {
                System.out.print("| \\ ");
            } else if (c == 1) {
                System.out.print("|    ");
            } else if (c == 4 || c == 5) {
                System.out.print("|    ");
            } else {
                // 일반 세로선: 공백 4개 -> 3개로 축소
                System.out.print("|   ");
            }
        }
    }

    public void printScore(double choScore, double hanScore) {
        System.out.println("================점 수===================");
        System.out.println("초: " + choScore + " vs 한: " + hanScore);
        System.out.println("======================================");
    }

    public void printResult(Team team) {
        System.out.println("게임이 종료되었습니다!");
        System.out.println("승자 : " + convertTeamTypeToKorean(team));
    }

    private String convertTeamTypeToKorean(Team team) {
        if (team == Team.CHO) {
            return CHO_KOREAN_DESCRIPTION;
        }
        return HAN_KOREAN_DESCRIPTION;
    }
}