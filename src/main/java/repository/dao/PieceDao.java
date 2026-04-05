package repository.dao;

public record PieceDao(
        String pieceType,
        String team,
        int rowIndex,
        int colIndex) {
}
