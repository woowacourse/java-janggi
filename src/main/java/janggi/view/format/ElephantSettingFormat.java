package janggi.view.format;

import janggi.domain.board.ElephantSetting;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum ElephantSettingFormat {

    LEFT_ELEPHANT("1", "상마상마", ElephantSetting.LEFT_ELEPHANT),
    RIGHT_ELEPHANT("2", "마상마상", ElephantSetting.RIGHT_ELEPHANT),
    INNER_ELEPHANT("3", "마상상마", ElephantSetting.INNER_ELEPHANT),
    OUTER_ELEPHANT("4", "상마마상", ElephantSetting.OUTER_ELEPHANT);

    private static final String INVALID_ELEPHANT_SETTING = "[ERROR] 존재하지 않는 상차림 입니다.";

    private final String command;
    private final String description;
    private final ElephantSetting elephantSetting;

    ElephantSettingFormat(String command, String description, ElephantSetting elephantSetting) {
        this.command = command;
        this.description = description;
        this.elephantSetting = elephantSetting;
    }

    public static ElephantSettingFormat from(String command) {
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

    public ElephantSetting toElephantSetting() {
        return elephantSetting;
    }
}
