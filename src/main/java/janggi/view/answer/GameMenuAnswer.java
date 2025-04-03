package janggi.view.answer;

import java.util.Arrays;

public enum GameMenuAnswer {
    NEW_GAME("1"),
    CONTINUED_GAME("2"),
    QUIT("q");

    private final String command;

    GameMenuAnswer(String command) {
        this.command = command;
    }

    public static GameMenuAnswer parse(final String number) {
        return Arrays.stream(GameMenuAnswer.values())
                .filter(answer -> answer.getCommand().equals(number))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 적절하지 않은 입력값입니다."));
    }

    public String getCommand() {
        return command;
    }
}
