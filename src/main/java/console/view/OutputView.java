package console.view;

import static console.util.BoardFormatter.COL_NUM;
import static console.util.BoardFormatter.RED;
import static console.util.BoardFormatter.RESET;
import static console.util.BoardFormatter.ROW_NUM;
import static console.util.BoardFormatter.SPACE;
import static console.util.BoardFormatter.VERTICAL_LINE;
import static console.util.BoardFormatter.formatHorizon;
import static console.util.BoardFormatter.formatSymbol;

import java.text.DecimalFormat;
import java.util.Map;
import model.Team;
import model.board.Board;
import model.coordinate.Position;
import model.piece.Piece;

public class OutputView {

    public static void displayBoard(Map<Position, Piece> board) {
        displayColIndex();
        String border = formatHorizon(Board.BOARD_COL);
        System.out.println(border);

        displayPositionByPiece(board);

        System.out.println(border);
    }

    public static void displayError(String message) {
        System.out.println(RED + "[ERROR] " + message + RESET);
    }

    public static void displayWinner(Team winner) {
        System.out.println(winner.getName() + " 승");
    }

    public static void displayScore(Map<Team, Double> finalScore) {
        DecimalFormat formatter = new DecimalFormat("#.#");
        finalScore.forEach((team, score) -> System.out.printf("%s: %s점%n", team.getName(), formatter.format(score)));
    }

    private static void displayPositionByPiece(Map<Position, Piece> board) {
        for (int row = 0; row < Board.BOARD_ROW; row++) {
            System.out.print(ROW_NUM[row] + " " + VERTICAL_LINE);
            for (int col = 0; col < Board.BOARD_COL; col++) {
                Piece piece = board.get(new Position(row, col));
                System.out.print(SPACE + formatSymbol(piece));
            }
            System.out.println(SPACE + VERTICAL_LINE);
        }
    }

    private static void displayColIndex() {
        System.out.println();
        System.out.print(SPACE + SPACE + SPACE);
        for (String column : COL_NUM) {
            System.out.print(SPACE + column);
        }
        System.out.println();
    }

    public static void displayNoGame() {
        System.out.println();
        System.out.println("진행한 게임이 없습니다.");
    }
}