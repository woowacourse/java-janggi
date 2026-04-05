package dto;

public record SavedPieceDto(
        String country,
        String pieceType,
        int x,
        int y
) {
}