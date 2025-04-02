package view;

import java.util.Arrays;

public enum PageCommand {
    LEFT_PAGE("1", -1),
    RIGHT_PAGE("2", 1),
    CURR_PAGE("3", 0);

    private String description;
    private int move;

    PageCommand(String description, int move) {
        this.description = description;
        this.move = move;
    }

    public String getDescription() {
        return description;
    }

    public int getMove() {
        return move;
    }

    public static PageCommand convertToCommand(String description) {
        return Arrays.stream(values())
                .filter(command -> command.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 커맨드 입니다."));
    }
}
