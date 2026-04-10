package janggi.view;

import janggi.controller.GameCommand;
import janggi.domain.board.FormationCommand;
import janggi.domain.player.Name;
import janggi.domain.space.Position;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class InputParser {
    private static final Pattern COMMA_SEPARATED_COORDINATES = Pattern.compile("^\\s*\\d+\\s*,\\s*\\d+\\s*$");
    private static final String DELIMITER = ",";
    private static final Map<String, FormationCommand> FORMATION_MAP = Map.of(
            "1", FormationCommand.FIRST,
            "2", FormationCommand.SECOND,
            "3", FormationCommand.THIRD,
            "4", FormationCommand.FOURTH
    );

    private static final Map<String, GameCommand> GAME_COMMAND_MAP = Map.of(
            "1", GameCommand.NEW_GAME,
            "2", GameCommand.LOAD_GAME
    );

    public static Name parseName(String input) {
        validateBlank(input);
        return new Name(input.strip());
    }

    public static GameCommand parseGameCommand(String input) {
        validateBlank(input);
        String strippedInput = input.strip();

        if (!GAME_COMMAND_MAP.containsKey(strippedInput)) {
            throw new IllegalArgumentException("올바른 게임 명령어가 아닙니다. (1 또는 2)");
        }
        return GAME_COMMAND_MAP.get(strippedInput);
    }

    public static Long parseGameId(String inputId) {
        try {
            return Long.valueOf(inputId.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("게임 ID는 숫자만 입력 가능합니다.");
        }
    }

    public static FormationCommand parseFormation(String input) {
        validateBlank(input);
        String strippedInput = input.strip();

        if (!FORMATION_MAP.containsKey(strippedInput)) {
            throw new IllegalArgumentException("올바른 포메이션 입력이 아닙니다. (1~4 사이의 숫자)");
        }
        return FORMATION_MAP.get(strippedInput);
    }

    public static Position parsePosition(String input) {
        validateBlank(input);
        if (!COMMA_SEPARATED_COORDINATES.matcher(input).matches()) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다. ex) 0,3");
        }

        return getPosition(input);
    }

    private static Position getPosition(String input) {
        List<Integer> coordinate = Arrays.stream(input.split(DELIMITER))
                .map(String::strip)
                .map(Integer::parseInt)
                .toList();

        return Position.of(coordinate.get(0), coordinate.get(1));
    }

    private static void validateBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("빈 값은 입력할 수 없습니다.");
        }
    }
}
