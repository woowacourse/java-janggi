package janggi.view.format;

import janggi.domain.board.ElephantFormation;
import janggi.domain.board.ElephantSetUp;
import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;
import java.util.Arrays;

public enum ElephantSetUpFormat {

    LEFT_ELEPHANT("1", "상마상마", ElephantSetUp.LEFT_ELEPHANT),
    RIGHT_ELEPHANT("2", "마상마상", ElephantSetUp.RIGHT_ELEPHANT),
    INNER_ELEPHANT("3", "마상상마", ElephantSetUp.INNER_ELEPHANT),
    OUTER_ELEPHANT("4", "상마마상", ElephantSetUp.OUTER_ELEPHANT),
    ;

    private final String command;
    private final String description;
    private final ElephantSetUp elephantSetUp;

    ElephantSetUpFormat(String command, String description, ElephantSetUp elephantSetUp) {
        this.command = command;
        this.description = description;
        this.elephantSetUp = elephantSetUp;
    }

    public static ElephantSetUpFormat findElephantSettingBy(String command) {
        return Arrays.stream(values())
                .filter(element -> element.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.INVALID_ELEPHANT_SET_UP_FORMAT.getMessage()));
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public ElephantFormation toElephantFormation(CampType campType) {
        return new ElephantFormation(campType, elephantSetUp);
    }
}
