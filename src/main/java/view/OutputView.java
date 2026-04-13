package view;

import domain.game.GameScore;
import domain.team.Team;
import dto.IntersectionsDto;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {
    private static final int MIN_INDEX = 0;
    private static final int MAX_ROW = 10;
    private static final int MAX_FILE = 9;
    private static final String FULL_SPACE = "　";
    private static final String HALF_SPACE = " ";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    public void printCurrentTurn(Team turn) {
        System.out.print(getTeamName(turn) + "의 차례입니다.\n");
    }

    public void printCurrentBoardStatus(final IntersectionsDto intersectionsDto) {
        final Map<String, String> board = intersectionsDto.getIntersections().stream()
                .collect(Collectors.toMap(
                        dto -> toPositionKey(dto.getY(), dto.getX()),
                        dto -> colorizePieceLabel(dto.getPieceLabel(), dto.getTeam())
                ));

        final String header = IntStream.range(MIN_INDEX, MAX_FILE)
                .mapToObj(this::toFullWidthNumber)
                .collect(Collectors.joining(HALF_SPACE));

        final String rows = IntStream.range(MIN_INDEX, MAX_ROW)
                .mapToObj(row -> {
                    final String rowCells = IntStream.range(MIN_INDEX, MAX_FILE)
                            .mapToObj(file -> board.getOrDefault(toPositionKey(row, file), "＋"))
                            .collect(Collectors.joining(HALF_SPACE));
                    return toFullWidthNumber(row) + HALF_SPACE + rowCells;
                })
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(FULL_SPACE + HALF_SPACE + header);
        System.out.println(rows);
    }

    private String toFullWidthNumber(int number) {
        return String.valueOf(number)
                .chars()
                .mapToObj(ch -> String.valueOf((char) ('０' + (ch - '0'))))
                .collect(Collectors.joining());
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printNoResumableGames() {
        System.out.println("재개할 수 있는 게임이 없습니다. 새 게임을 시작합니다.");
    }

    public void printResumableGamesHeader() {
        System.out.println("재개 가능한 게임 목록 (한 점수 / 초 점수 / 마지막 수정)");
    }

    public void printResumableGameLine(int index, double hanScore, double choScore, String updatedAtText) {
        System.out.printf("%d. 한 %.1f / 초 %.1f / %s%n", index, hanScore, choScore, updatedAtText);
    }

    public void printWinnerTeam(Team winnerTeam) {
        System.out.println("게임이 종료되었습니다.");
        System.out.println(getTeamName(winnerTeam) + "의 승리입니다.\n");
    }

    public void printCurrentScore(GameScore score) {
        System.out.printf("현재 점수 - 초(楚): %.1f점, 한(漢): %.1f점%n", score.cho(), score.han());
    }

    private String getTeamName(Team team) {
        if (team == Team.CHO) {
            return "초(楚)";
        }
        return "한(漢)";
    }

    private String toPositionKey(int y, int x) {
        return y + "," + x;
    }

    private String colorizePieceLabel(String pieceLabel, String team) {
        if ("CHO".equals(team)) {
            return ANSI_GREEN + pieceLabel + ANSI_RESET;
        }
        if ("HAN".equals(team)) {
            return ANSI_RED + pieceLabel + ANSI_RESET;
        }
        return pieceLabel;
    }
}
