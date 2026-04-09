package janggi.dto;

public record PieceLogDto(Long id, Long turnId, String pieceType, String teamType, int x, int y) {
}
