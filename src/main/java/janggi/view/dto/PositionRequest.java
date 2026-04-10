package janggi.view.dto;

public record PositionRequest(int row, int column, String howPlaying) {
    private static final String gamePlay = "y";
    private static final String giveUp = "q";
    private static final String draw = "d";

    public static PositionRequest from(String input) {
        if (input.equals(giveUp) || input.equals(draw)) {
            return new PositionRequest(-1, -1, input);
        }

        int spaceIndex = input.indexOf(" ");
        if (spaceIndex == -1) {
            throw new IllegalArgumentException("좌표는 '행 열' 형식으로 입력해주세요.");
        }
        int row = Integer.parseInt(input.substring(0, spaceIndex).trim());
        int column = Integer.parseInt(input.substring(spaceIndex + 1).trim());
        return new PositionRequest(row, column, gamePlay);
    }
}
