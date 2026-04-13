package console.view;

import java.util.stream.Stream;

public enum GameMenuCommand {

    CONTINUE(1, "게임 이어서 진행하기"),
    NEW_GAME(2, "새로 시작하기"),
    END(3, "종료하기");

    private final int value;
    private final String description;

    GameMenuCommand(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static GameMenuCommand select(int number) {
        return Stream.of(values())
                .filter(v -> v.value == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."));
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
