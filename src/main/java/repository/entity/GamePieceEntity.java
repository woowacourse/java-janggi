package repository.entity;

public record GamePieceEntity(
        Long gamePieceId,
        Long gameId,
        Long pieceId,
        int row,
        int col,
        boolean isActive
) {
}
