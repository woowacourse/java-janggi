package view;

import domain.Coordinate;
import domain.Team;
import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Goong;
import domain.piece.Jol;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import java.util.Map;
import java.util.Optional;

public class OutputView {

    private static final Map<Class<? extends Piece>, String> PIECE_NAMES = Map.of(
        Cha.class, "차",
        Ma.class, "마",
        Sang.class, "상",
        Sa.class, "사",
        Goong.class, "궁",
        Po.class, "포",
        Jol.class, "졸",
        Byeong.class, "병"
    );

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
        return TEAM_COLORS.get(team) + PIECE_NAMES.get(piece.getClass()) + RESET;
    }
}
