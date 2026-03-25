package janggi.view;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.command.SetupCommand;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import janggi.dto.BoardDto;
import java.util.List;
import java.util.Map;

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

    public static void printBoard(final BoardDto boardDto) {
        final List<String> rowStatuses = boardDto.rowStatuses();

        for (String rowStatus : rowStatuses) {
            System.out.println(rowStatus);
        }
    }
}
