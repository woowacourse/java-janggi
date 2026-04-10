package view;

import java.util.Arrays;

public enum MainMenu {
    NEW_GAME(1),
    LOAD_GAME(2),
    EXIT(3);

    private final int code;

    MainMenu(int code) {
        this.code = code;
    }

    public static MainMenu from(int code) {
        return Arrays.stream(values())
                .filter(menu -> menu.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("1 ~ 3 사이의 숫자로 입력해주세요."));
    }
}
