package janggi.dto;

public record MoveCommandDto(
        char sourceRow,
        char sourceCol,
        String pieceName,
        char destinationRow,
        char destinationCol) {

    public static MoveCommandDto from(String source, String pieceName, String destinationCol) {
        return new MoveCommandDto(source.charAt(0), source.charAt(1), pieceName, destinationCol.charAt(0), destinationCol.charAt(1));
    }

    public static MoveCommandDto from(String input) {
        String[] split = input.split(" ");
        String source = split[0];
        String pieceName = split[1];
        String destination = split[2];
        return new MoveCommandDto(source.charAt(0), source.charAt(1), pieceName, destination.charAt(0), destination.charAt(1));
    }
}
