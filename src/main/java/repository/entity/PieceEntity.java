package repository.entity;

public record PieceEntity(
        Long pieceId,
        int row,
        int col,
        String team,
        String pieceType
) {
}
