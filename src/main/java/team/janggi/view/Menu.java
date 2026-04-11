package team.janggi.view;

public enum Menu {
    CREATE_ROOM(1, "게임방 생성"),
    JOIN_ROOM(2, "게임방 입장"),
    EXIT(3, "종료");

    private final int number;
    private final String description;

    Menu(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int number() {
        return number;
    }

    public String description() {
        return description;
    }

     public static Menu of(int number) {
        for (Menu menu : values()) {
            if (menu.number == number) {
                return menu;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 메뉴 번호입니다: " + number);
    }
}
