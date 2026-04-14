package janggi.view.dto;

public record PositionRequest(int row, int column, Command command) {

    public static PositionRequest from(String input) {
        Command command = Command.from(input);
        if (command.isGiveUp() || command.isDraw()) {
            return new PositionRequest(-1, -1, command);
        }

        int spaceIndex = input.indexOf(" ");
        if (spaceIndex == -1) {
            throw new IllegalArgumentException("좌표는 '행 열' 형식으로 입력해주세요.");
        }
        int row = Integer.parseInt(input.substring(0, spaceIndex).trim());
        int column = Integer.parseInt(input.substring(spaceIndex + 1).trim());
        return new PositionRequest(row, column, command);
    }

    public boolean isGiveUp() {
        return command == Command.GIVE_UP;
    }

    public boolean isDraw() {
        return command == Command.DRAW;
    }
}
