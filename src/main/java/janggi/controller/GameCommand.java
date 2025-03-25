package janggi.controller;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum GameCommand {
    MOVE("\\d{2} \\S \\d{2}"),
    QUIT("[qQ]")
    ;

    private final Pattern pattern;

    GameCommand(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public static GameCommand from(String input) {
        return Arrays.stream(GameCommand.values())
                .filter(gameCommand -> gameCommand.pattern.matcher(input).matches())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령 입력입니다."));
    }
}
