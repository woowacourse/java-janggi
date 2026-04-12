package janggi.repository;

public record StoredGamePiece(
        int rowPosition,
        int columnPosition,
        String pieceType,
        String camp
) {
}
