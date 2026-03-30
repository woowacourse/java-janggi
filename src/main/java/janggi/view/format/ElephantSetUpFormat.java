package janggi.view.format;

import janggi.domain.board.ElephantSetUp;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum ElephantSetUpFormat {

    LEFT_ELEPHANT("1", "상마상마", ElephantSetUp.LEFT_ELEPHANT),
    RIGHT_ELEPHANT("2", "마상마상", ElephantSetUp.RIGHT_ELEPHANT),
    INNER_ELEPHANT("3", "마상상마", ElephantSetUp.INNER_ELEPHANT),
    OUTER_ELEPHANT("4", "상마마상", ElephantSetUp.OUTER_ELEPHANT);

    private static final String INVALID_ELEPHANT_SETTING = "[ERROR] 존재하지 않는 상차림 입니다.";

    private final String command;
    private final String description;
    private final ElephantSetUp elephantSetUp;

    ElephantSetUpFormat(String command, String description, ElephantSetUp elephantSetUp) {
        this.command = command;
        this.description = description;
        this.elephantSetUp = elephantSetUp;
    }

    public static ElephantSetUpFormat from(String command) {
        return Arrays.stream(values())
                .filter(format -> format.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ELEPHANT_SETTING));
    }

    public static String outputMessage() {
        return Arrays.stream(values())
                .map(format -> format.command + ". " + format.description)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public ElephantSetUp toElephantSetting() {
        return elephantSetUp;
    }
}
