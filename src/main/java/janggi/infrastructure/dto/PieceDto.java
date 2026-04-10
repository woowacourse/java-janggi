package janggi.infrastructure.dto;

public record PieceDto(
        int x,
        int y,
        String pieceType,
        String team
) {
}