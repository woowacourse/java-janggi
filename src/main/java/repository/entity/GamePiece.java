package repository.entity;

public record GamePiece(
        Long gamePieceId,
        Long gameId,
        Long pieceId,
        int row,
        int col,
        boolean isActive
) {
}
