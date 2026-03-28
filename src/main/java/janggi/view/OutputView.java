package janggi.view;

import janggi.domain.command.SetupCommand;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.dto.BoardDto;
import java.util.List;

public final class OutputView {

    private static final String ERROR_PREFIX = "[ERROR]: ";

    private OutputView() {
    }

    public static void printSetupGuide(TeamType teamType) {
        System.out.println(teamType.getName() + "의 차림법을 입력해주세요.");
        for (final SetupCommand setupCommand : SetupCommand.values()) {
            System.out.println(setupCommand.ordinal() + ". " + setupCommand.getDescription());
        }
    }

    public static void printErrorMessage(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public static void printBoard(final BoardDto boardDto) {
        final List<String> rowStatuses = boardDto.rowStatuses();

        for (String rowStatus : rowStatuses) {
            System.out.println(rowStatus);
        }
    }

    public static void printTurnStatus(final Team team) {
        System.out.printf("%s 차례입니다. ", team.getName());
    }
}
