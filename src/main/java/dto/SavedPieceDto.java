package dto;

public record SavedPieceDto(
        String country,
        String pieceType,
        int row,
        int col
) {
}