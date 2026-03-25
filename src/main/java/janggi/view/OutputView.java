package janggi.view;

import janggi.domain.command.SetupCommand;
import janggi.domain.team.TeamType;

public final class OutputView {
    private static final String ERROR_PREFIX = "[ERROR]: ";

    private OutputView() {
    }

    public static void printSetupGuide(TeamType teamType) {
        System.out.println(teamType.getName() + "의 차림법을 입력해주세요.");
        for (final SetupCommand setupCommand : SetupCommand.values()) {
            System.out.println(setupCommand.getNumber() + ". " + setupCommand.getDescription());
        }
    }

    public static void printErrorMessage(String message) {
        System.out.println(ERROR_PREFIX + message);
    }
}
