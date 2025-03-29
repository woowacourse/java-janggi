package janggi.dao.dto;

public record BoardPieceFindResponse(
        int x,
        int y,
        String pieceType,
        String side) {
}
