package database.dto;

public record IntersectionDto(
        int y,
        int x,
        String pieceType,
        String teamName,
        String intersectionType
) {
}
