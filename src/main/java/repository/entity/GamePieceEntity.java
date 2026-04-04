package repository.entity;

public record GamePieceEntity(
        Long id,
        Long gameId,
        String pieceType,
        String team,
        int row,
        int col,
        boolean isActive
) {
}
