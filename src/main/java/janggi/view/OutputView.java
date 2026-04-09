package janggi.view;

import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.command.SetupCommand;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.dto.BoardDto;
import janggi.dto.GameResultDto;
import java.util.List;

public final class OutputView {

    private static final String ERROR_PREFIX = "[ERROR]: ";

    private OutputView() {
    }

    public static void printGameSelect(final List<String> gameNames,
        final int maximumGamesInProgressCount) {
        System.out.printf("불러올 게임을 선택하세요. (최대 %d개 저장 가능)\n", maximumGamesInProgressCount);
        for (int gameIndex = 1; gameIndex <= gameNames.size(); gameIndex++) {
            System.out.printf("%d. %s\n", gameIndex, gameNames.get(gameIndex - 1));
        }
        if (gameNames.size() < maximumGamesInProgressCount) {
            System.out.println("0. 새 게임 생성");
        }
    }

    public static void printSetupGuide(final TeamType teamType) {
        System.out.println(teamType.getName() + "의 차림법을 입력해주세요.");
        for (final SetupCommand setupCommand : SetupCommand.values()) {
            System.out.println(setupCommand.ordinal() + 1 + ". " + setupCommand.getDescription());
        }
    }

    public static void printErrorMessage(final String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public static void printGameCreationMessage() {
        System.out.println("새 게임 생성을 선택하였습니다. 새로운 게임을 시작합니다.");
    }

    public static void printGameLoadingMessage(final String gameName) {
        System.out.printf("%s의 진행 상태를 불러옵니다.\n", gameName);
    }

    public static void printBoard(final BoardDto boardDto) {
        final List<String> rowStatuses = boardDto.rowStatuses();

        System.out.println("   " + boardDto.columns());
        for (int row = 1; row <= MAXIMUM_ROW; row++) {
            System.out.printf("%2d %s\n", row, rowStatuses.get(row - 1));
        }

        boardDto.teamScoreMap().forEach((team, score) ->
            System.out.printf("%s 점수: %.1f\n", team, score));
    }

    public static void printTurnStatus(final Team team) {
        System.out.printf("%s 차례입니다. ", team.getName());
    }

    public static void printGameResult(final GameResultDto gameResultDto) {
        System.out.printf("%s가 승리했습니다! 게임을 종료합니다.\n", gameResultDto.winnerTeam());
    }
}
