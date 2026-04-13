package dto;

import java.util.Arrays;

public enum GameMenu {
    EXIT(0, "프로그램 종료"),
    NEW_GAME(1, "새 게임"),
    SHOW_PREVIOUS_GAMES(2, "이전 게임 조회(이어하기/결과 확인)"),
    ;

    private final int menuId;
    private final String description;

    GameMenu(int menuId, String description) {
        this.menuId = menuId;
        this.description = description;
    }

    public static GameMenu from(int menuId) {
        return Arrays.stream(values())
                .filter(menu -> menu.menuId == menuId)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(menuId + "번은 존재하지 않는 메뉴입니다."));
    }

    public boolean isDispatchable() {
        return this != EXIT;
    }

    public String toDisplayString() {
        return "%d. %s".formatted(menuId, description);
    }
}
