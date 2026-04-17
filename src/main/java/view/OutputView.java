package view;

import domain.Team;
import domain.dto.JanggiBoardDto;
import domain.dto.PieceDto;
import domain.piece.PieceType;

public class OutputView {
    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLUMNS = 9;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public void printBoard(JanggiBoardDto boardDto) {
        printColumnIndices();

        PieceDto[][] grid = new PieceDto[BOARD_ROWS][BOARD_COLUMNS];
        for (PieceDto pieceDto : boardDto.getPieces()) {
            grid[pieceDto.row()][pieceDto.col()] = pieceDto;
        }

        for (int row = 0; row < BOARD_ROWS; row++) {
            System.out.printf("%2d ", row);
            printRow(grid, row);
            System.out.println();
        }
    }

    public void printResult(double choScore, double hanScore) {
        System.out.println("\n--- 최종 점수 ---");
        System.out.printf("초(CHO) 팀: %.1f점\n", choScore);
        System.out.printf("한(HAN) 팀: %.1f점\n", hanScore);
        System.out.println("----------------");

        if (choScore > hanScore) {
            System.out.println(ANSI_BLUE + "초(CHO)가 승리하였습니다!" + ANSI_RESET);
        }
        if (hanScore > choScore) {
            System.out.println(ANSI_RED + "한(HAN)이 승리하였습니다!" + ANSI_RESET);
        }
        if (choScore == hanScore) {
            System.out.println("무승부입니다!");
        }
    }

    private void printColumnIndices() {
        System.out.print("   ");
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            System.out.print(col + "．");
        }
        System.out.println();
    }

    private void printRow(PieceDto[][] grid, int row) {
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            PieceDto pieceDto = grid[row][col];
            if (pieceDto == null) {
                System.out.print("． ");
                continue;
            }
            System.out.print(withTeamColor(pieceDto) + " ");
        }
    }

    private String withTeamColor(PieceDto pieceDto) {
        String symbol = getSymbol(pieceDto.pieceType());
        Team team = pieceDto.team();
        if (team == Team.HAN) {
            return ANSI_RED + symbol + ANSI_RESET;
        }
        if (team == Team.CHO) {
            return ANSI_BLUE + symbol + ANSI_RESET;
        }
        return symbol;
    }

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }


    private String getSymbol(PieceType pieceType) {
        if (pieceType == PieceType.CANNON) {
            return "포";
        }
        if (pieceType == PieceType.CAR) {
            return "차";
        }
        if (pieceType == PieceType.ELEPHANT) {
            return "상";
        }
        if (pieceType == PieceType.GUARD) {
            return "사";
        }
        if (pieceType == PieceType.HORSE) {
            return "마";
        }
        if (pieceType == PieceType.KING) {
            return "궁";
        }
        if (pieceType == PieceType.PAWN) {
            return "졸";
        }
        return "．";
    }
}
