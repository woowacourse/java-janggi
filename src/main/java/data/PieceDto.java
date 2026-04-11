package data;

public record PieceDto(
        Long boardId,
        String pieceType,
        String side,
        int row,
        int column) {
}
