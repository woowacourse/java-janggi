package view;

import domain.Team;
import domain.JanggiBoard;
import domain.piece.Cannon;
import domain.piece.Car;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.position.Position;

public class OutputView {
    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLUMNS = 9;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public void printBoard(JanggiBoard board) {
        printColumnIndices();
        for (int row = 0; row < BOARD_ROWS; row++) {
            System.out.printf("%2d ", row);
            printRow(board, row);
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

    private void printRow(JanggiBoard board, int row) {
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            Position position = new Position(row, col);
            Piece piece = board.getPiece(position);
            System.out.print(withTeamColor(piece) + " ");
        }
    }

    private String withTeamColor(Piece piece) {
        String symbol = getSymbol(piece);
        Team team = piece.getTeam();
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


    private String getSymbol(Piece piece) {
        if (piece instanceof Cannon) {
            return "포";
        }
        if (piece instanceof Car) {
            return "차";
        }
        if (piece instanceof Elephant) {
            return "상";
        }
        if (piece instanceof Guard) {
            return "사";
        }
        if (piece instanceof Horse) {
            return "마";
        }
        if (piece instanceof King) {
            return "궁";
        }
        if (piece instanceof Pawn) {
            return "졸";
        }
        return "．";
    }
}
