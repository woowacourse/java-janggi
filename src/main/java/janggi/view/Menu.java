package janggi.view;

import java.util.Arrays;

public enum Menu {

    MOVE("1"),
    SAVE("2"),
    QUIT("3"),
    ;

    private final String input;

    Menu(String input) {
        this.input = input;
    }

    public static Menu of(String input) {
        return Arrays.stream(values())
                .filter(menu -> menu.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바르지 않은 메뉴 선택입니다."));
    }
}
