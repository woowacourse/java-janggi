package janggi.controller;

public enum GameSelect {
    CREATE("1", "게임 생성"),
    LOAD("2", "게임 로드"),
    QUIT("Q", "종료");

    private final String value;
    private final String format;

    GameSelect(String value, String format) {
        this.value = value;
        this.format = format;
    }

    public static GameSelect from(String selectValue) {
        for (GameSelect gameSelect : values()) {
            if (gameSelect.value.equals(selectValue)) {
                return gameSelect;
            }
        }
        throw new IllegalArgumentException("해당하는 선택지가 없습니다! 숫자만 입력해주세요");
    }

    public String getFormatMessage() {
        return value + ". " + format;
    }
}
