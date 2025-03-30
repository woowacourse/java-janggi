package janggi.dto;

public record PieceDto(
        int id,
        int pieceTypeId,
        int teamId,
        int x,
        int y
) {
}
