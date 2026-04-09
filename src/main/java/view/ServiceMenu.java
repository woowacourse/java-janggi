package view;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ServiceMenu {

    SHOW_MOVE_HISTORY(1, "게임 기록 재생하기"),
    PLAY_SAVED_GAME(2, "새로운 게임 하기"),
    PLAY_NEW_GAME(3, "게임 이어 하기"),
    ;

    private final int number;
    private final String display;

    ServiceMenu(int number, String display) {
        this.number = number;
        this.display = display;
    }

    public static ServiceMenu from(int menuNumber) {
        return Arrays.stream(values())
            .filter(menu -> menu.number == menuNumber)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("번호와 일치하는 서비스가 없습니다."));
    }

    public static String convertDisplayFormat() {
        return Arrays.stream(values())
            .map(menu -> menu.number + ". " + menu.display)
            .collect(Collectors.joining("\n"));
    }

    public boolean isShowMoveHistory() {
        return this == SHOW_MOVE_HISTORY;
    }
}
