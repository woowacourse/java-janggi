package janggi.dto;

public record PieceDto(
        String nameWithColor,
        PositionDto position
) {

    private static final String ANSI_RESET = "\u001B[0m";

    public static PieceDto from(String name, String color, PositionDto position) {
        return new PieceDto(color + name + ANSI_RESET, position);
    }

}
