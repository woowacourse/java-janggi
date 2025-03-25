package domain;

import domain.piece.Position;
import java.util.Arrays;

public enum SetUp {

    INNER_ELEPHANT("1",
            new Position(3, 1),
            new Position(2, 1)
    ),
    OUTER_ELEPHANT("2",
            new Position(2, 1),
            new Position(3, 1)
    ),
    RIGHT_ELEPHANT("3",
            new Position(3, 1),
            new Position(2, 1)
    ),
    LEFT_ELEPHANT("4",
            new Position(2, 1),
            new Position(3, 1)
    ),
    ;

    private final String command;
    private final Position hanHorsePosition;
    private final Position hanElephantPosition;

    SetUp(String command, Position hanHorsePosition, Position hanElephantPosition) {
        this.command = command;
        this.hanHorsePosition = hanHorsePosition;
        this.hanElephantPosition = hanElephantPosition;
    }

    public static SetUp getValue(String input) {
        return Arrays.stream(SetUp.values())
                .filter(setUp -> setUp.command.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 선택입니다."));
    }
}
