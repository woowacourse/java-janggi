package janggi.view;

import static janggi.view.ViewTools.FULL_WIDTH_BAR;
import static janggi.view.ViewTools.FULL_WIDTH_SPACE;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.Team;
import java.util.Map;
import java.util.Optional;

public class OutputView {

    private static final Map<Team, String> TEAM_NAMES = Map.of(
        Team.HAN, "한",
        Team.CHO, "초"
    );

    private static final Map<Team, String> TEAM_COLORS = Map.of(
        Team.HAN, ViewTools.RED,
        Team.CHO, ViewTools.BLUE
    );

    public void printException(Exception e) {
        System.err.println("[ERROR] " + e.getMessage());
        System.err.println();
    }

    public void printBoard(Map<Coordinate, Piece> pieces) {
        System.out.println("   １　２　３　４　５　６　７　８　９");

        for (int height = 1; height <= 10; height++) {
            System.out.printf("%2d ", height);

            for (int width = 1; width <= 9; width++) {
                Coordinate coordinate = new Coordinate(width, height);
                Piece piece = pieces.get(coordinate);
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

    public void printScore(final Map<Team, Double> teamScores) {
        System.out.println("각 팀의 점수입니다.");
        teamScores.forEach((team, score) ->
            System.out.printf("%s 나라 : %.1f%n", applyColor(team), score));
        System.out.println();
    }

    public void printSaveAndQuit() {
        System.out.println("현재 보드판 상태로 게임을 저장하고 종료합니다.");
        System.out.println("- (데이터베이스 연결에 실패했다면 저장되지 않습니다.)");
        System.out.println();
    }

    public void printFinished(Team winTeam) {
        System.out.println("게임이 종료되었습니다.");
        String teamName = applyColor(winTeam);
        System.out.printf("%s 나라의 승리입니다.%n", teamName);
        System.out.println();
    }

    private String applyColor(Piece piece) {
        Team team = piece.team();
        return ViewTools.applyColor(TEAM_COLORS.get(team), piece.pieceType().canonicalName());
    }

    private String applyColor(Team team) {
        return ViewTools.applyColor(TEAM_COLORS.get(team), TEAM_NAMES.get(team));
    }
}
