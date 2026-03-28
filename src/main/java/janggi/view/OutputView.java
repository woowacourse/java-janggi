package janggi.view;

import janggi.domain.team.TeamType;
import janggi.dto.BoardDto;
import java.util.List;

public final class OutputView {
    private static final String ERROR_PREFIX = "[ERROR]: ";
    private static final List<String> ELEPHANT_FORMATION_DESCRIPTIONS = List.of(
            "1. 안상 차림",
            "2. 바깥상 차림",
            "3. 왼상 차림",
            "4. 오른상 차림"
    );

    private OutputView() {
    }

    public static void printSetupGuide(TeamType teamType) {
        System.out.println(teamType.getName() + "의 차림법을 입력해주세요.");
        for (final String description : ELEPHANT_FORMATION_DESCRIPTIONS) {
            System.out.println(description);
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
}
