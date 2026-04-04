package janggi.controller;

public enum GameSelect {
    CREATE(1),
    LOAD(2),
    ;
    private final int value;

    GameSelect(int value) {
        this.value = value;
    }

    public static GameSelect from(int selectValue) {
        for (GameSelect gameSelect : values()) {
            if (gameSelect.value == selectValue) {
                return gameSelect;
            }
        }
        throw new IllegalArgumentException("해당하는 선택지가 없습니다!");
    }
}
