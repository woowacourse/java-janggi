package janggi.infrastructure.dao.dto;

public record PieceEntity(
        Long id,
        Long gameId,
        int x,
        int y,
        String side,
        String pieceType
) {
}
