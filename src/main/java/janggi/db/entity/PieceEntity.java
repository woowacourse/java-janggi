package janggi.db.entity;

public record PieceEntity(
        String pieceType,
        String side,
        int rowIndex,
        int colIndex,
        int gameId
) {
}
