package janggi.view.dto;

public record PositionRequest(int row, int column) {
    public static PositionRequest from(String input) {
        int spaceIndex = input.indexOf(" ");
        if (spaceIndex == -1) {
            throw new IllegalArgumentException("좌표는 'x y' 형식으로 입력해주세요.");
        }
        int row = Integer.parseInt(input.substring(0, spaceIndex).trim());
        int column = Integer.parseInt(input.substring(spaceIndex + 1).trim());
        return new PositionRequest(row, column);
    }
}
