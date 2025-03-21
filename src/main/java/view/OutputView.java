package view;

import coordinate.Coordinate;
import java.util.Map;
import java.util.Optional;
import piece.Piece;
import team.Team;

public class OutputView {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    private static final Map<Team, String> TEAM_COLORS = Map.of(
            Team.HAN, RED,
            Team.CHO, BLUE
    );

    private static final String FULL_WIDTH_BAR = "＿";
    private static final String FULL_WIDTH_SPACE = "　";

    public void printBoard(Map<Coordinate, Piece> board) {
        System.out.println("   １　２　３　４　５　６　７　８　９");

        for (int height = 1; height <= 10; height++) {
            System.out.printf("%2d ", height);

            for (int width = 1; width <= 9; width++) {
                Coordinate coordinate = new Coordinate(width, height);
                Piece piece = board.get(coordinate);
                printPiece(piece);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printPiece(Piece piece) {
        Optional.ofNullable(piece).ifPresentOrElse(
                p -> System.out.print(applyColor(p) + FULL_WIDTH_SPACE),
                () -> System.out.print(FULL_WIDTH_BAR + FULL_WIDTH_SPACE)
        );
    }

    private String applyColor(Piece piece) {
        Team team = piece.getTeam();
        return TEAM_COLORS.get(team) + piece.getName() + RESET;
    }
}
